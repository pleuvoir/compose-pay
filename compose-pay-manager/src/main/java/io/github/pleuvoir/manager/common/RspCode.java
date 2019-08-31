package io.github.pleuvoir.manager.common;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 返回码
 * 
 */
public enum RspCode {

	SUCCESS("SUCCESS","成功"),
	ERROR("ERROR", "系统忙，请稍后再试");

	private RspCode(String code, String msg){
		this.code = code;
		this.msg = msg;
	}
	
	private String code;
	
	private String msg;
	
	
	public boolean isEquals(String code) {
		return code!=null && StringUtils.equals(this.code, code);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return this.code+":"+msg;
	}
	
}
