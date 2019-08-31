package io.github.pleuvoir.manager.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 菜单树的使用者
 */
public enum MenuUser {
	
	/** main页面*/
	MAIN("main","main页面"),
	/** 菜单service */
	MENU_SERVICE("menu_Service", "菜单service");
	
	private String code;
	
	private String msg;
	
	private MenuUser(String code, String msg){
		this.code = code;
		this.msg = msg;
	}
	
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

}
