package io.github.pleuvoir.manager.common.util.net;

import java.io.Serializable;
import java.util.Map;

/**
 * 请求返回响应对象
 * @author HaiBo
 * 2017年12月20日 上午11:43:34
 */
public class RespObj implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8615005349716770154L;

	private Map<String, String> respHeaders;
	private String respMsg;
	
	public Map<String, String> getRespHeaders() {
		return respHeaders;
	}
	public void setRespHeaders(Map<String, String> respHeaders) {
		this.respHeaders = respHeaders;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
}
