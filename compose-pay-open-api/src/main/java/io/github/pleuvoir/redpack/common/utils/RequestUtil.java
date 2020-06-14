package io.github.pleuvoir.redpack.common.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestUtil {

    private static final String LEFT_BRACE = "{";
    private static final String RIGHT_BRACE = "}";
    private static final String DOUBLE_QUOTATION_MARKS = "\"";
    private static final String COLON = ":";
    private static final String COMMA = ",";

    /**
     * 将请求参数转换成可视的json格式<br>
     * <b>注意</b>，该方法不考虑参数值的类型，统一按字符串处理，并且不考虑一个参数名多个值的问题，均按一个值处理
     *
     * @param req
     * @return
     */
    public static String paramsToJsonString(HttpServletRequest req) {
        Enumeration<String> names = req.getParameterNames();
        Enumeration<String> heards = req.getHeaderNames();

        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append(LEFT_BRACE);
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = req.getParameter(name);
            jsonBuilder.append(DOUBLE_QUOTATION_MARKS);
            jsonBuilder.append(name);
            jsonBuilder.append(DOUBLE_QUOTATION_MARKS);
            jsonBuilder.append(COLON);
            jsonBuilder.append(DOUBLE_QUOTATION_MARKS);
            jsonBuilder.append(value);
            jsonBuilder.append(DOUBLE_QUOTATION_MARKS);
            jsonBuilder.append(COMMA);
        }
        if (StringUtils.endsWith(jsonBuilder, COMMA)) {
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        }
        jsonBuilder.append(RIGHT_BRACE);

        jsonBuilder.append(LEFT_BRACE);
        while (heards.hasMoreElements()) {
            String name = heards.nextElement();
            String value = req.getHeader(name);
            jsonBuilder.append(DOUBLE_QUOTATION_MARKS);
            jsonBuilder.append(name);
            jsonBuilder.append(DOUBLE_QUOTATION_MARKS);
            jsonBuilder.append(COLON);
            jsonBuilder.append(DOUBLE_QUOTATION_MARKS);
            jsonBuilder.append(value);
            jsonBuilder.append(DOUBLE_QUOTATION_MARKS);
            jsonBuilder.append(COMMA);
        }
        if (StringUtils.endsWith(jsonBuilder, COMMA)) {
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        }
        jsonBuilder.append(RIGHT_BRACE);

        return jsonBuilder.toString();
    }

    /**
     * 检查是否是ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(requestType)) {
            return true;
        }
        return false;
    }

    /**
     * 提取User-Agent
     *
     * @param request
     * @return
     */
    public static String userAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    /**
     * 提取User-Agent，并截取指定的长度
     *
     * @param request
     * @param start
     * @param length
     * @return
     */
    public static String userAgent(HttpServletRequest request, int start, int length) {
        return StringUtils.substring(userAgent(request), start, start + length);
    }

    /**
     * 获取请求的IP
     *
     * @param request
     * @return
     */
    public static String ipAddress(HttpServletRequest request) {
        String ip = validIp(request.getHeader("x-forwarded-for"));
        if (StringUtils.isBlank(ip)) {
            ip = validIp(request.getHeader("Proxy-Client-IP"));
        }
        if (StringUtils.isBlank(ip)) {
            ip = validIp(request.getHeader("WL-Proxy-Client-IP"));
        }
        if (StringUtils.isBlank(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "localhost";
        }
        return ip;
    }

    /**
     * 获取请求查询参数
     *
     * @param request
     * @return
     */
    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Enumeration<String> names = request.getParameterNames();
        Map<String, String> parameters = new HashMap<>();
        while (names.hasMoreElements()) {
            String parametersName = names.nextElement();
            String parametersValue = request.getParameter(parametersName);
            parameters.put(parametersName, parametersValue);
        }

        return parameters;
    }

    /**
     * 获取请求头信息
     *
     * @param request
     * @return
     */
    public static Map<String, String> getHeaders(HttpServletRequest request) {
        Enumeration<String> names = request.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (names.hasMoreElements()) {
            String headerName = names.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.put(headerName, headerValue);
        }

        return headers;
    }

    /**
     * 获取请求body（使用 CachingRequestWrapper）
     *
     * @return
     * @throws IOException
     */
//    public static String getBody(HttpServletRequest request) throws IOException {
//        String body = StringUtils.EMPTY;
//
//        if (request instanceof CachingRequestWrapper) {
//            CachingRequestWrapper requestWrapper = (CachingRequestWrapper) request;
//            body = IOUtils.toString(requestWrapper.getBody(), request.getCharacterEncoding());
//        }
//        return body;
//    }

    private static String validIp(String ipStr) {
        if (StringUtils.isBlank(ipStr)) {
            return null;
        }
        String[] ips = ipStr.split(",");
        for (String ip : ips) {
            if (!"unknown".equalsIgnoreCase(ip))
                return ip.trim();
        }
        return null;
    }
}
