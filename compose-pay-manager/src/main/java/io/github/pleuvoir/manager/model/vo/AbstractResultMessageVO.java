package io.github.pleuvoir.manager.model.vo;

import com.alibaba.fastjson.JSON;

import io.github.pleuvoir.manager.common.RspCode;

import java.io.Serializable;

public abstract class AbstractResultMessageVO implements Serializable {

	private String code;//返回码
	
	private String msg;//返回信息
	
	public AbstractResultMessageVO(){}
	
	public AbstractResultMessageVO(RspCode rspCode){
		this.code = rspCode.getCode();
		this.msg = rspCode.getMsg();
	}
	
	public AbstractResultMessageVO(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public AbstractResultMessageVO(RspCode rspCode, String msg) {
		this.code = rspCode.getCode();
		this.msg = msg;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public void setCode(RspCode rspCode) {
		this.code = rspCode.getCode();
		this.msg = rspCode.getMsg();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
