package io.github.pleuvoir.pay.common.net;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import javax.net.ssl.SSLContext;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

/**
 * 同步HTTP请求客户端，该类建议以spring bean的形式进行初始化
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Slf4j
public class SyncHttpClient {

    //最大连接数
    @Setter
    private int maxTotal = 1000;

    //默认的每个路由最大线程数
    @Setter
    private int defaultMaxPerRoute = 60;

    //从连接管理器中获取连接的超时时间
    @Setter
    private int connectionRequestTimeout = 5000;

    //连接超时时间
    @Setter
    private int connectTimeout = 10000;

    //读数据超时时间
    @Setter
    private int socketTimeout = 30000;

    @Setter
    private boolean expectContinueEnabled = true;

    //设置每个路由的最大连接数
    private Map<HttpRoute, Integer> maxPerRoute = new ConcurrentHashMap<>();

    //请求配置
    private RequestConfig requestConfig;

    //请求客户端
    private CloseableHttpClient httpclient = null;

    //不重置超时时间标识值
    private static final int NO_RESET_TIMEOUT = -0xFF;


    /**
     * 设置连接池的每个路由的最大线程数
     *
     * @param routes 每个路由的最大线程数，key为目标地址uri（scheme://hostname:port），value为连接数
     */
    public void setMaxPerRoute(Map<String, Integer> routes) {
        if (maxPerRoute == null || maxPerRoute.isEmpty()) {
            log.warn("设置的单个路由的最大线程数失败，参数为空");
            return;
        }
        for (Map.Entry<String, Integer> entity : routes.entrySet()) {
            Integer val = entity.getValue();
            if (val == null || val < 1) {
                log.warn("单个路由的最大线程数不能小于1，当前设置，路由：{}，连接数：{}", entity.getKey(), val);
                throw new IllegalArgumentException("单个路由的最大线程数不能小于1，当前设置，路由：" + entity.getKey() + "，连接数：" + val);
            }
            URI uri = URI.create(entity.getKey());
            this.maxPerRoute.put(new HttpRoute(new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme())), val);
        }
    }

    /**
     * 初始化<br>
     * <ol>
     *     <li>信任所有证书</li>
     *     <li>注册http和https</li>
     *     <li>创建连接池</li>
     *     <li>创建默认请求配置</li>
     *     <li>创建同步请求客户端</li>
     * </ol>
     */
    public void init() {

        //信任所有证书
        SSLContext sslContext = null;
        try {
            sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) {
                    return true;
                }
            }).build();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            log.error("信任所有证书异常，初始化失败。", e);
            return;
        }

        //注册http和https
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier()))
                .build();

        //创建连接池（连接管理器）
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(this.maxTotal); //最大连接数
        connectionManager.setDefaultMaxPerRoute(this.defaultMaxPerRoute); //默认的每个路由最大线程数
        if (this.maxPerRoute != null && !this.maxPerRoute.isEmpty()) {
            for (Entry<HttpRoute, Integer> routeInfo : this.maxPerRoute.entrySet()) {
                connectionManager.setMaxPerRoute(routeInfo.getKey(), routeInfo.getValue());
            }
        }

        //创建默认请求配置
        this.requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .setExpectContinueEnabled(expectContinueEnabled)
                .build();

        //创建同步请求客户端
        this.httpclient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .setDefaultCookieStore(new NoopCookieStore())
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false)) //不进行重试
                .build();
    }

    /**
     * 发送GET请求，可以携带cookie，并将返回结果转换成字符串
     *
     * @param uri      请求地址，可带查询参数
     * @param cookies  cookie参数，为空时表示不携带cookie
     * @param encoding 结果转换成字符串时的编码
     * @return 返回的响应结果内容
     * @throws IOException
     */
    public String doGetWithCookie(String uri, Map<String, String> cookies, Charset encoding) throws IOException {
        return doGetWithCookie(uri, cookies, encoding, NO_RESET_TIMEOUT, NO_RESET_TIMEOUT);
    }

    /**
     * 发送GET请求，可以携带header参数，并将返回结果转换成字符串
     *
     * @param uri            请求地址，可带查询参数
     * @param headers        header参数，为空时表示不携带header
     * @param encoding       结果转换成字符串时的编码
     * @param connectTimeout 当次请求的连接超时时间
     * @param socketTimeout  当次请求的数据传输超时时间
     * @return 返回的响应结果内容
     * @throws IOException
     */
    public String doGetWithHeader(String uri, Map<String, String> headers, Charset encoding, int connectTimeout, int socketTimeout)
            throws IOException {
        if (headers != null && !headers.isEmpty()) {
            List<Header> headerList = new ArrayList<>();
            for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
                headerList.add(new BasicHeader(headerEntry.getKey(), headerEntry.getValue()));
            }
            return get(uri, headerList.toArray(new Header[headerList.size()]), encoding, connectTimeout, socketTimeout);
        }
        return get(uri, null, encoding, connectTimeout, socketTimeout);
    }

    /**
     * 发送GET请求，可以携带cookie，并将返回结果转换成字符串
     *
     * @param uri            请求地址，可带查询参数
     * @param cookies        cookie参数，为空时表示不携带cookie
     * @param encoding       结果转换成字符串时的编码
     * @param connectTimeout 当次请求的连接超时时间
     * @param socketTimeout  当次请求的数据传输超时时间
     * @return 返回的响应结果内容
     * @throws IOException
     */
    public String doGetWithCookie(String uri, Map<String, String> cookies, Charset encoding, int connectTimeout, int socketTimeout)
            throws IOException {
        if (cookies != null && !cookies.isEmpty()) {
            StringBuilder buffer = new StringBuilder();
            for (Map.Entry<String, String> cookieEntry : cookies.entrySet()) {
                buffer.append(cookieEntry.getKey())
                        .append("=")
                        .append(cookies.get(cookieEntry.getValue()))
                        .append("; ");
            }
            //设置cookie内容
            Header header = new BasicHeader("Cookie", buffer.toString());
            return get(uri, new Header[]{header}, encoding, connectTimeout, socketTimeout);
        }
        return get(uri, null, encoding, connectTimeout, socketTimeout);
    }

    /**
     * 发送GET请求，并将响应结果转换为字符串
     *
     * @param uri      请求地址，可带查询参数
     * @param encoding 请求编码
     * @return 返回的响应结果内容
     * @throws IOException
     */
    public String doGet(String uri, Charset encoding) throws IOException {
        return this.get(uri, null, encoding, NO_RESET_TIMEOUT, NO_RESET_TIMEOUT);
    }

    /**
     * 发送GET请求，并将响应结果转换为字符串
     *
     * @param uri            请求地址，可带查询参数
     * @param encoding       请求编码
     * @param connectTimeout 连接超时时间
     * @param socketTimeout  数据读取超时时间
     * @return 返回的响应结果内容
     * @throws IOException
     */
    public String doGet(String uri, Charset encoding, int connectTimeout, int socketTimeout) throws IOException {
        return this.get(uri, null, encoding, connectTimeout, socketTimeout);
    }

    /**
     * 发送GET请求，并将响应结果转换为字符串
     *
     * @param uri            请求地址，可带查询参数
     * @param headers        请求Header（可选）
     * @param encoding       请求编码
     * @param connectTimeout 连接超时时间
     * @param socketTimeout  数据读取超时时间
     * @return 返回的响应结果内容
     * @throws IOException
     */
    public String get(String uri, Header[] headers, Charset encoding, int connectTimeout, int socketTimeout) throws IOException {
        HttpGet get = new HttpGet(uri);

        RequestConfig requestConfig = this.resetRequestConfig(connectTimeout, socketTimeout);
        get.setConfig(requestConfig);

        if (ArrayUtils.isNotEmpty(headers)) {
            get.setHeaders(headers);
        }
        CloseableHttpResponse response = this.httpclient.execute(get);
        return this.responseToString(response, encoding);
    }


    /**
     * 发送POST请求，并将返回结果转换成字符串
     *
     * @param uri            请求地址，可带查询参数
     * @param headers        header参数，为空时表示不携带header
     * @param params         body参数，为空时表示不携带参数
     * @param encoding       结果转换成字符串时的编码
     * @param connectTimeout 当次请求的连接超时时间
     * @param socketTimeout  当次请求的数据传输超时时间
     * @return 返回的响应结果内容
     * @throws IOException
     */
    public ResponseBody doPost(String uri, Map<String, String> headers, Map<String, String> params, Charset encoding,
            int connectTimeout, int socketTimeout) throws IOException {
        Header[] headerArray = null;
        if (headers != null && !headers.isEmpty()) {
            List<Header> headerList = new ArrayList<>();
            for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
                headerList.add(new BasicHeader(headerEntry.getKey(), headerEntry.getValue()));
            }
            headerArray = headerList.toArray(new Header[headerList.size()]);
        }
        HttpEntity paramEntity = null;
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> paramList = new ArrayList<>();
            for (Map.Entry<String, String> param : params.entrySet()) {
                paramList.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            }
            paramEntity = new UrlEncodedFormEntity(paramList, encoding);
        }
        return post(uri, headerArray, paramEntity, encoding, connectTimeout, socketTimeout);
    }

    /**
     * 发送POST请求，并将返回结果转换成字符串
     *
     * @param uri            请求地址，可带查询参数
     * @param params         请求body，为空时表示不携带body
     * @param encoding       结果转换成字符串时的编码
     * @param connectTimeout 当次请求的连接超时时间
     * @param socketTimeout  当次请求的数据传输超时时间
     * @return 返回的响应结果内容
     * @throws IOException
     */
    public ResponseBody doPostWithParam(String uri, String params, Charset encoding, int connectTimeout, int socketTimeout)
            throws IOException {
        return doPost(uri, null, params, encoding, connectTimeout, socketTimeout);
    }

    /**
     * 发送POST请求，并将返回结果转换成字符串
     *
     * @param uri      请求地址，可带查询参数
     * @param params   请求body，为空时表示不携带body
     * @param encoding 结果转换成字符串时的编码
     * @return 返回的响应结果内容
     * @throws IOException
     */
    public ResponseBody doPostWithParam(String uri, String params, Charset encoding) throws IOException {
        return doPost(uri, null, params, encoding, NO_RESET_TIMEOUT, NO_RESET_TIMEOUT);
    }

    /**
     * 发送POST请求，并将返回结果转换成字符串
     *
     * @param uri      请求地址，可带查询参数
     * @param headers  header参数，为空时表示不携带header
     * @param data     请求body，为空时表示不携带body
     * @param encoding 结果转换成字符串时的编码
     * @return 返回的响应结果内容
     * @throws IOException
     */
    public ResponseBody doPost(String uri, Map<String, String> headers, String data, Charset encoding) throws IOException {
        return doPost(uri, headers, data, encoding, NO_RESET_TIMEOUT, NO_RESET_TIMEOUT);
    }

    /**
     * 发送POST请求，并将返回结果转换成字符串
     *
     * @param uri            请求地址，可带查询参数
     * @param headers        header参数，为空时表示不携带header
     * @param data           请求body，为空时表示不携带body
     * @param encoding       结果转换成字符串时的编码
     * @param connectTimeout 当次请求的连接超时时间
     * @param socketTimeout  当次请求的数据传输超时时间
     * @return 返回的响应结果内容
     * @throws IOException
     */
    public ResponseBody doPost(String uri, Map<String, String> headers, String data, Charset encoding, int connectTimeout,
            int socketTimeout) throws IOException {
        Header[] headerArray = null;
        if (headers != null && !headers.isEmpty()) {
            List<Header> headerList = new ArrayList<>();
            for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
                headerList.add(new BasicHeader(headerEntry.getKey(), headerEntry.getValue()));
            }
            headerArray = headerList.toArray(new Header[headerList.size()]);
        }
        HttpEntity paramEntity = null;
        if (StringUtils.isNotBlank(data)) {
            paramEntity = new StringEntity(data, encoding);
        }
        return post(uri, headerArray, paramEntity, encoding, connectTimeout, socketTimeout);
    }

    private ResponseBody post(String uri, Header[] headers, HttpEntity entity, Charset encoding, int connectTimeout, int socketTimeout)
            throws IOException {
        HttpPost post = new HttpPost(uri);
        RequestConfig config = resetRequestConfig(connectTimeout, socketTimeout);
        post.setConfig(config);

        if (ArrayUtils.isNotEmpty(headers)) {
            post.setHeaders(headers);
        }
        if (entity != null) {
            post.setEntity(entity);
        }
        CloseableHttpResponse response = httpclient.execute(post);

        Map<String, String> responseHeaders = new HashMap<>();
        for (Header header : response.getAllHeaders()) {
            responseHeaders.put(header.getName(), header.getValue());
        }

        ResponseBody responseBody = new ResponseBody();
        responseBody.setResponseHeaders(responseHeaders);
        responseBody.setResponseString(this.responseToString(response, encoding));

        return responseBody;
    }

    /**
     * 重新设置连接超时和数据读取超时时间，若小于等于0则忽略对应设置
     *
     * @param connectTimeout 连接超时时间
     * @param socketTimeout  数据读取超时时间
     */
    private RequestConfig resetRequestConfig(Integer connectTimeout, Integer socketTimeout) {

        //都小于0则返回全局配置，避免重建对象
        if (connectTimeout <= 0 && socketTimeout <= 0) {
            return this.requestConfig;
        }

        Builder builder = RequestConfig.copy(this.requestConfig);
        if (connectTimeout > 0) {
            builder.setConnectTimeout(connectTimeout);
        }
        if (socketTimeout > 0) {
            builder.setSocketTimeout(socketTimeout);
        }
        return builder.build();
    }


    /**
     * 将响应内容转换成字符串
     */
    private String responseToString(CloseableHttpResponse response, Charset encoding) throws IOException {
        if (response.getEntity() == null) {
            return null;
        }
        HttpEntity entity = response.getEntity();
        try {
            return EntityUtils.toString(entity, encoding);
        } finally {
            response.close();
        }
    }


    /**
     * 空实现cookie存储器，不存储cookie
     */
    public static class NoopCookieStore implements CookieStore {

        //线程安全
        private Vector<Cookie> cookies = new Vector<>();

        @Override
        public void addCookie(Cookie cookie) {
        }

        @Override
        public List<Cookie> getCookies() {
            return cookies;
        }

        @Override
        public boolean clearExpired(Date date) {
            return false;
        }

        @Override
        public void clear() {

        }
    }

    @Data
    private static class ResponseBody {

        private Map<String, String> responseHeaders;
        private String responseString;
    }

}
