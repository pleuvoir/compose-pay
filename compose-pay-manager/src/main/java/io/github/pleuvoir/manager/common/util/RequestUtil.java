package io.github.pleuvoir.manager.common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class RequestUtil {
	/**
	 * 检查是否是ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request){
		String requestType = request.getHeader("X-Requested-With");
		if("XMLHttpRequest".equals(requestType)){
			return true;
		}
		return false;
	}
	
	/**
	 * 提取User-Agent
	 * @param request
	 * @return
	 */
	public static String userAgent(HttpServletRequest request){
		return request.getHeader("User-Agent");
	}
	/**
	 * 提取User-Agent，并截取指定的长度
	 * @param request
	 * @param start
	 * @param length
	 * @return
	 */
	public static String userAgent(HttpServletRequest request, int start, int length){
		return StringUtils.substring(userAgent(request), start, start + length);
	}
	
	/**
	 * 获取请求的IP
	 * @param request
	 * @return
	 */
	public static String ipAddress(HttpServletRequest request){
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
	
	private static String validIp(String ipStr){
		if(StringUtils.isBlank(ipStr)){
			return null;
		}
		String[] ips = ipStr.split(",");
		for(String ip: ips){
			if(!"unknown".equalsIgnoreCase(ip))
				return ip.trim();
		}
		return null;
	}
}
