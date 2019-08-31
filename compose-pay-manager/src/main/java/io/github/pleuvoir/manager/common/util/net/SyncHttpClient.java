package io.github.pleuvoir.manager.common.util.net;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
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
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 同步http请求客户端
 * @author abeir
 *
 */
public class SyncHttpClient {
	
	private static Logger logger = LoggerFactory.getLogger(SyncHttpClient.class);
	
	//标识不重设超时时间
	private static final int NOOP_RESET_TIMEOUT = -0xFF;
	
	// 最大连接数
	private int maxTotal = 1000;
	// 默认的最大每个路由线程数
	private int defaultMaxPerRoute = 60;
	// 从连接管理器中获取连接的超时时间
	private int connectionRequestTimeout = 5000;
	// 连接超时时间
	private int connectTimeout = 10000;
	// 读数据超时时间
	private int socketTimeout = 30000;
	
	private boolean expectContinueEnabled = true;
	//设置每个路由的最大连接数
	private Map<HttpRoute,Integer> maxPerRoute = new ConcurrentHashMap<>();
	
	private CloseableHttpClient httpclient = null;
	
	private RequestConfig requestConfig;
	
	/**
	 * 设置连接池的默认的路由线程数
	 * @param defaultMaxPerRoute 默认的路由线程数
	 * @return
	 */
	public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
		this.defaultMaxPerRoute = defaultMaxPerRoute;
	}
	/**
	 * 设置连接池的每个路由的最大线程数
	 * @param routes 每个路由的最大线程数，key为目标地址uri（scheme://hostname:port），value为连接数
	 * @return
	 */
	public void setMaxPerRoute(Map<String,Integer> routes) {
		if(maxPerRoute==null || maxPerRoute.isEmpty()) {
			logger.warn("设置的单个路由的最大线程数失败，参数为空");
			return;
		}
		for(Map.Entry<String,Integer> entity : routes.entrySet()) {
			Integer val = entity.getValue();
			if(val==null || val<0) {
				logger.warn("单个路由的最大线程数不能小于1，当前设置，路由：{}，连接数：{}", entity.getKey(), String.valueOf(val));
				throw new IllegalArgumentException("单个路由的最大线程数不能小于1，当前设置，路由：" + entity.getKey() + "，连接数：" + String.valueOf(val)) ;
			}
			URI uri = URI.create(entity.getKey());
			this.maxPerRoute.put(new HttpRoute(new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme())), val);
		}
	}
	
	/**
	 * 设置连接池的最大总线程数
	 * @param maxTotal 最大总线程数
	 */
	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}
	
	/**
	 * 设置单个请求从连接管理器中获取连接的超时时间
	 * @param connectionRequestTimeout 从连接管理器中获取连接的超时时间（毫秒）
	 */
	public void setConnectionRequestTimeout(int connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
	}
	/**
	 * 设置单个请求建立连接的超时时间
	 * @param connectTimeout 建立连接的超时时间（毫秒）
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * 设置单个请求的SO_TIMEOUT，数据传输处理超时时间
	 * @param socketTimeout SO_TIMEOUT，数据传输处理超时时间（毫秒）
	 */
	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public void setExpectContinueEnabled(boolean expectContinueEnabled) {
		this.expectContinueEnabled = expectContinueEnabled;
	}
	
	
	public SyncHttpClient() {
	}
	
	/**
	 * 初始化<br>
	 * <ol>
	 * 	<li>信任所有证书</li>
	 *  <li>注册http和https</li>
	 *  <li>创建连接池</li>
	 *  <li>创建默认请求配置</li>
	 *  <li>创建同步请求客户端</li>
	 * </ol>
	 */
	public void init() {
		
		SSLContext sslContext = null;
		try {
			sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {

				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			logger.error("设置信任所有证书发生异常，初始化失败", e);
			return;
		}
		
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
			.register("http", PlainConnectionSocketFactory.getSocketFactory())
			.register("https", new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier()))
			.build();
		
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
		connManager.setMaxTotal(maxTotal);
		connManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
		if(maxPerRoute!=null && !maxPerRoute.isEmpty()) {
			for(Map.Entry<HttpRoute,Integer> entry : maxPerRoute.entrySet()) {
				connManager.setMaxPerRoute(entry.getKey(), entry.getValue());
			}
		}
		
		requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(connectionRequestTimeout)
				.setConnectTimeout(connectTimeout)
				.setSocketTimeout(socketTimeout)
				.setExpectContinueEnabled(expectContinueEnabled)
				.build();
		
		httpclient = HttpClients.custom()
				.setConnectionManager(connManager)
				.setDefaultRequestConfig(requestConfig)
				.setDefaultCookieStore(new NoopCookieStore())
				.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
				.build();
	}
	
	/**
	 * 将响应内容转换成字符串
	 */
	private String responseToString(CloseableHttpResponse response, Charset encoding) throws IOException {
		HttpEntity entity = response.getEntity();
		try {
			if (entity != null) {
				return EntityUtils.toString(entity, encoding);
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}
	
	/**
	 * 重设请求的连接超时时间和数据获取超时时间<br>
	 * 若连接超时时间和数据获取超时时间都小于等于0，则不重设，直接返回原{@link RequestConfig}
	 */
	private RequestConfig rebuildRequestConfig(int connectTimeout, int socketTimeout) {
		if(connectTimeout==NOOP_RESET_TIMEOUT && socketTimeout==NOOP_RESET_TIMEOUT) {
			return this.requestConfig;
		}
		Builder builder = RequestConfig.copy(requestConfig);
		if(connectTimeout>0) {
			builder.setConnectTimeout(connectTimeout);
		}
		if(socketTimeout>0) {
			builder.setSocketTimeout(socketTimeout);
		}
		return builder.build();
	}
	
	private String get(String uri, Header[] headers, Charset encoding, int connectTimeout, int socketTimeout) throws IOException {
		HttpGet get = new HttpGet(uri);
		
		RequestConfig config = rebuildRequestConfig(connectTimeout, socketTimeout);
		get.setConfig(config);
		
		if(ArrayUtils.isNotEmpty(headers)) {
			get.setHeaders(headers);
		}
		CloseableHttpResponse response = httpclient.execute(get);
		return responseToString(response, encoding);
	}
	
	/**
	 * 发送GET请求，并将返回结果转换成字符串
	 * @param uri 请求地址，可带查询参数
	 * @param encoding 结果转换成字符串时的编码
	 * @return 返回的响应结果内容
	 * @throws IOException
	 */
	public String sendGet(String uri, Charset encoding) throws IOException {
		return sendGet(uri, encoding, NOOP_RESET_TIMEOUT, NOOP_RESET_TIMEOUT);
	}
	/**
	 * 发送GET请求，并将返回结果转换成字符串
	 * @param uri 请求地址，可带查询参数
	 * @param encoding 结果转换成字符串时的编码
	 * @param connectTimeout 当次请求的连接超时时间
	 * @param socketTimeout 当次请求的数据传输超时时间
	 * @return 返回的响应结果内容
	 * @throws IOException
	 */
	public String sendGet(String uri, Charset encoding, int connectTimeout, int socketTimeout) throws IOException {
		return get(uri, null, encoding, connectTimeout, socketTimeout);
	}
	/**
	 * 发送GET请求，可以携带header参数，并将返回结果转换成字符串
	 * @param uri 请求地址，可带查询参数
	 * @param headers header参数，为空时表示不携带header
	 * @param encoding 结果转换成字符串时的编码
	 * @return 返回的响应结果内容
	 * @throws IOException
	 */
	public String sendGetWithHeader(String uri, Map<String, String> headers, Charset encoding) throws IOException {
		return sendGetWithHeader(uri, headers, encoding, NOOP_RESET_TIMEOUT, NOOP_RESET_TIMEOUT);
	}
	/**
	 * 发送GET请求，可以携带header参数，并将返回结果转换成字符串
	 * @param uri 请求地址，可带查询参数
	 * @param headers header参数，为空时表示不携带header
	 * @param encoding 结果转换成字符串时的编码
	 * @param connectTimeout 当次请求的连接超时时间
	 * @param socketTimeout 当次请求的数据传输超时时间
	 * @return 返回的响应结果内容
	 * @throws IOException
	 */
	public String sendGetWithHeader(String uri, Map<String, String> headers, Charset encoding, int connectTimeout, int socketTimeout) throws IOException {
		if (headers != null && !headers.isEmpty()) {
			List<Header> headerList = new ArrayList<>();
			for(Map.Entry<String,String> headerEntry : headers.entrySet()) {
				headerList.add(new BasicHeader(headerEntry.getKey(), headerEntry.getValue()));
			}
			return get(uri, headerList.toArray(new Header[headerList.size()]), encoding, connectTimeout, socketTimeout);
		}
		return get(uri, null, encoding, connectTimeout, socketTimeout);
	}
	/**
	 * 发送GET请求，可以携带cookie，并将返回结果转换成字符串
	 * @param uri 请求地址，可带查询参数
	 * @param cookies cookie参数，为空时表示不携带cookie
	 * @param encoding 结果转换成字符串时的编码
	 * @return 返回的响应结果内容
	 * @throws IOException
	 */
	public String sendGetWithCookie(String uri, Map<String, String> cookies, Charset encoding) throws IOException {
		return sendGetWithCookie(uri, cookies, encoding, NOOP_RESET_TIMEOUT, NOOP_RESET_TIMEOUT);
	}
	/**
	 * 发送GET请求，可以携带cookie，并将返回结果转换成字符串
	 * @param uri 请求地址，可带查询参数
	 * @param cookies cookie参数，为空时表示不携带cookie
	 * @param encoding 结果转换成字符串时的编码
	 * @param connectTimeout 当次请求的连接超时时间
	 * @param socketTimeout 当次请求的数据传输超时时间
	 * @return 返回的响应结果内容
	 * @throws IOException
	 */
	public String sendGetWithCookie(String uri, Map<String, String> cookies, Charset encoding, int connectTimeout, int socketTimeout) throws IOException {
		if (cookies != null && !cookies.isEmpty()) {
			StringBuilder buffer = new StringBuilder();
			for(Map.Entry<String,String> cookieEntry : cookies.entrySet()) {
				buffer.append(cookieEntry.getKey())
					.append("=")
					.append(cookies.get(cookieEntry.getValue()))
					.append("; ");
			}
			// 设置cookie内容
			Header header = new BasicHeader("Cookie", buffer.toString());
			return get(uri, new Header[] {header}, encoding, connectTimeout, socketTimeout);
		}
		return get(uri, null, encoding, connectTimeout, socketTimeout);
	}
	
	
	private RespObj post(String uri, Header[] headers, HttpEntity entity, Charset encoding, int connectTimeout, int socketTimeout) throws IOException {
		HttpPost post = new HttpPost(uri);
		RequestConfig config = rebuildRequestConfig(connectTimeout, socketTimeout);
		post.setConfig(config);
		if(ArrayUtils.isNotEmpty(headers)) {
			post.setHeaders(headers);
		}
		if(entity!=null) {
			post.setEntity(entity);
		}
		CloseableHttpResponse response = httpclient.execute(post);
		
		Header[] responseHeaders = response.getAllHeaders();
		Map<String, String> resHeaderMap = new HashMap<String, String>();
		for (Header header : responseHeaders) {
			resHeaderMap.put(header.getName(), header.getValue());
		}
		
		RespObj respObj = new RespObj();
		respObj.setRespHeaders(resHeaderMap);
		respObj.setRespMsg(responseToString(response, encoding));
		
		return respObj;
	}
	
	/**
	 * 发送POST请求，并将返回结果转换成字符串
	 * @param uri 请求地址，可带查询参数
	 * @param encoding 结果转换成字符串时的编码
	 * @return 返回的响应结果内容
	 * @throws IOException
	 */
	public RespObj sendPost(String uri, Charset encoding) throws IOException {
		return sendPost(uri, encoding, NOOP_RESET_TIMEOUT, NOOP_RESET_TIMEOUT);
	}
	/**
	 * 发送POST请求，并将返回结果转换成字符串
	 * @param uri 请求地址，可带查询参数
	 * @param encoding 结果转换成字符串时的编码
	 * @param connectTimeout 当次请求的连接超时时间
	 * @param socketTimeout 当次请求的数据传输超时时间
	 * @return 返回的响应结果内容
	 * @throws IOException
	 */
	public RespObj sendPost(String uri, Charset encoding, int connectTimeout, int socketTimeout) throws IOException {
		return post(uri, null, null, encoding, connectTimeout, socketTimeout);
	}
	/**
	 * 发送POST请求，并将返回结果转换成字符串
	 * @param uri 请求地址，可带查询参数
	 * @param headers header参数，为空时表示不携带header
	 * @param encoding 结果转换成字符串时的编码
	 * @return 返回的响应结果内容
	 * @throws IOException
	 */
	public RespObj sendPostWithHeader(String uri, Map<String, String> headers, Charset encoding) throws IOException {
		return sendPostWithHeader(uri, headers, encoding, NOOP_RESET_TIMEOUT, NOOP_RESET_TIMEOUT);
	}
	/**
	 * 发送POST请求，并将返回结果转换成字符串
	 * @param uri 请求地址，可带查询参数
	 * @param headers header参数，为空时表示不携带header
	 * @param encoding 结果转换成字符串时的编码
	 * @param connectTimeout 当次请求的连接超时时间
	 * @param socketTimeout 当次请求的数据传输超时时间
	 * @return 返回的响应结果内容
	 * @throws IOException
	 */
	public RespObj sendPostWithHeader(String uri, Map<String, String> headers, Charset encoding, int connectTimeout, int socketTimeout) throws IOException {
		if (headers != null && !headers.isEmpty()) {
			List<Header> headerList = new ArrayList<>();
			for(Map.Entry<String,String> headerEntry : headers.entrySet()) {
				headerList.add(new BasicHeader(headerEntry.getKey(), headerEntry.getValue()));
			}
			return post(uri, headerList.toArray(new Header[headerList.size()]), null, encoding, connectTimeout, socketTimeout);
		}
		return post(uri, null, null, encoding, connectTimeout, socketTimeout);
	}
	/**
	 * 发送POST请求，并将返回结果转换成字符串
	 * @param uri 请求地址，可带查询参数
	 * @param cookies cookie参数，为空时表示不携带cookie
	 * @param encoding 结果转换成字符串时的编码
	 * @return 返回的响应结果内容
	 * @throws IOException
	 */
	public RespObj sendPostWithCookie(String uri, Map<String, String> cookies, Charset encoding) throws IOException {
		return sendPostWithCookie(uri, cookies, encoding, NOOP_RESET_TIMEOUT, NOOP_RESET_TIMEOUT);
	}
	/**
	 * 发送POST请求，并将返回结果转换成字符串
	 * @param uri 请求地址，可带查询参数
	 * @param cookies cookie参数，为空时表示不携带cookie
	 * @param encoding 结果转换成字符串时的编码
	 * @param connectTimeout 当次请求的连接超时时间
	 * @param socketTimeout 当次请求的数据传输超时时间
	 * @return 返回的响应结果内容
	 * @throws IOException
	 */
	public RespObj sendPostWithCookie(String uri, Map<String, String> cookies, Charset encoding, int connectTimeout, int socketTimeout) throws IOException {
		if (cookies != null && !cookies.isEmpty()) {
			StringBuilder buffer = new StringBuilder();
			for(Map.Entry<String,String> cookieEntry : cookies.entrySet()) {
				buffer.append(cookieEntry.getKey())
					.append("=")
					.append(cookies.get(cookieEntry.getValue()))
					.append("; ");
			}
			// 设置cookie内容
			Header header = new BasicHeader("Cookie", buffer.toString());
			return post(uri, new Header[] {header}, null, encoding, connectTimeout, socketTimeout);
		}
		return post(uri, null, null, encoding, connectTimeout, socketTimeout);
	}
	/**
	 * 发送POST请求，并将返回结果转换成字符串
	 * @param uri 请求地址，可带查询参数
	 * @param params body参数，为空时表示不携带参数
	 * @param encoding 结果转换成字符串时的编码
	 * @return 返回的响应结果内容
	 * @throws IOException
	 */
	public RespObj sendPostWithParam(String uri, Map<String, String> params, Charset encoding) throws IOException {
		return sendPost(uri, null, params, encoding);
	}
	/**
	 * 发送POST请求，并将返回结果转换成字符串
	 * @param uri 请求地址，可带查询参数
	 * @param params body参数，为空时表示不携带参数
	 * @param encoding 结果转换成字符串时的编码
	 * @param connectTimeout 当次请求的连接超时时间
	 * @param socketTimeout 当次请求的数据传输超时时间
	 * @return 返回的响应结果内容
	 * @throws IOException
	 */
	public RespObj sendPostWithParam(String uri, Map<String, String> params, Charset encoding, int connectTimeout, int socketTimeout) throws IOException {
		return sendPost(uri, null, params, encoding, connectTimeout, socketTimeout);
	}
	/**
	 * 发送POST请求，并将返回结果转换成字符串
	 * @param uri 请求地址，可带查询参数
	 * @param params 请求body，为空时表示不携带body
	 * @param encoding 结果转换成字符串时的编码
	 * @return 返回的响应结果内容
	 * @throws IOException
	 */
	public RespObj sendPostWithParam(String uri, String params, Charset encoding) throws IOException {
		return sendPost(uri, null, params, encoding, NOOP_RESET_TIMEOUT, NOOP_RESET_TIMEOUT);
	}
	/**
	 * 发送POST请求，并将返回结果转换成字符串
	 * @param uri 请求地址，可带查询参数
	 * @param params 请求body，为空时表示不携带body
	 * @param encoding 结果转换成字符串时的编码
	 * @param connectTimeout 当次请求的连接超时时间
	 * @param socketTimeout 当次请求的数据传输超时时间
	 * @return 返回的响应结果内容
	 * @throws IOException
	 */
	public RespObj sendPostWithParam(String uri, String params, Charset encoding, int connectTimeout, int socketTimeout) throws IOException {
		return sendPost(uri, null, params, encoding, connectTimeout, socketTimeout);
	}
	/**
	 * 发送POST请求，并将返回结果转换成字符串
	 * @param uri 请求地址，可带查询参数
	 * @param headers header参数，为空时表示不携带header
	 * @param params body参数，为空时表示不携带参数
	 * @param encoding 结果转换成字符串时的编码
	 * @return 返回的响应结果内容
	 * @throws IOException
	 */
	public RespObj sendPost(String uri, Map<String, String> headers, Map<String, String> params, Charset encoding) throws IOException {
		return sendPost(uri, headers, params, encoding, NOOP_RESET_TIMEOUT, NOOP_RESET_TIMEOUT);
	}
	/**
	 * 发送POST请求，并将返回结果转换成字符串
	 * @param uri 请求地址，可带查询参数
	 * @param headers header参数，为空时表示不携带header
	 * @param params body参数，为空时表示不携带参数
	 * @param encoding 结果转换成字符串时的编码
	 * @param connectTimeout 当次请求的连接超时时间
	 * @param socketTimeout 当次请求的数据传输超时时间
	 * @return 返回的响应结果内容
	 * @throws IOException
	 */
	public RespObj sendPost(String uri, Map<String, String> headers, Map<String, String> params, Charset encoding, int connectTimeout, int socketTimeout) throws IOException {
		Header[] headerArray = null;
		if (headers != null && !headers.isEmpty()) {
			List<Header> headerList = new ArrayList<>();
			for(Map.Entry<String,String> headerEntry : headers.entrySet()) {
				headerList.add(new BasicHeader(headerEntry.getKey(), headerEntry.getValue()));
			}
			headerArray = headerList.toArray(new Header[headerList.size()]);
		}
		HttpEntity paramEntity = null;
		if (params!=null && !params.isEmpty()) {
			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			for(Map.Entry<String,String> param : params.entrySet()) {
				paramList.add(new BasicNameValuePair(param.getKey(), param.getValue()));
			}
			paramEntity = new UrlEncodedFormEntity(paramList, encoding);
		}
		return post(uri, headerArray, paramEntity, encoding, connectTimeout, socketTimeout);
	}
	/**
	 * 发送POST请求，并将返回结果转换成字符串
	 * @param uri 请求地址，可带查询参数
	 * @param headers header参数，为空时表示不携带header
	 * @param data 请求body，为空时表示不携带body
	 * @param encoding 结果转换成字符串时的编码
	 * @return 返回的响应结果内容
	 * @throws IOException
	 */
	public RespObj sendPost(String uri, Map<String, String> headers, String data, Charset encoding) throws IOException {
		return sendPost(uri, headers, data, encoding, NOOP_RESET_TIMEOUT, NOOP_RESET_TIMEOUT);
	}
	/**
	 * 发送POST请求，并将返回结果转换成字符串
	 * @param uri 请求地址，可带查询参数
	 * @param headers header参数，为空时表示不携带header
	 * @param data 请求body，为空时表示不携带body
	 * @param encoding 结果转换成字符串时的编码
	 * @param connectTimeout 当次请求的连接超时时间
	 * @param socketTimeout 当次请求的数据传输超时时间
	 * @return 返回的响应结果内容
	 * @throws IOException
	 */
	public RespObj sendPost(String uri, Map<String, String> headers, String data, Charset encoding, int connectTimeout, int socketTimeout) throws IOException {
		Header[] headerArray = null;
		if (headers != null && !headers.isEmpty()) {
			List<Header> headerList = new ArrayList<>();
			for(Map.Entry<String,String> headerEntry : headers.entrySet()) {
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
	
}
