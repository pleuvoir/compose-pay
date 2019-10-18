package io.github.pleuvoir.gateway.model.vo;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

import io.github.pleuvoir.gateway.constants.MessageCodeEnum;
import io.github.pleuvoir.gateway.constants.ResultCodeEnum;
import lombok.Data;

/**
 * 所有返回消息的父类
 * @author pleuvoir
 * 
 */
@Data
public class ResultMessageVO implements Serializable {

	private static final long serialVersionUID = 9208791844887896579L;

	/**
	 * 返回码
	 */
	private String code;

	/**
	 * 返回信息
	 */
	private String msg;

	/***
	 * 错误代码描述
	 */
	private String errCodeDes;

	/***
	 * 错误代码
	 */
	private String errCode;

	public ResultMessageVO() {
	}

	public ResultMessageVO(MessageCodeEnum msgCode) {
		this.code = msgCode.getCode();
		this.msg = msgCode.getMsg();
	}

	public ResultMessageVO(MessageCodeEnum msgCode, ResultCodeEnum resultCode) {
		this.code = msgCode.getCode();
		this.msg = resultCode.getMsg();
	}

	public ResultMessageVO(MessageCodeEnum msgCode, String msg) {
		this.code = msgCode.getCode();
		this.msg = msg;
	}

	public ResultMessageVO(MessageCodeEnum msgCode, ResultCodeEnum resultCode, String msg) {
		this.code = msgCode.getCode();
		this.errCode = resultCode.getCode();
		this.errCodeDes = msg;
	}

	public ResultMessageVO(ResultCodeEnum resultCode) {
		this.code = resultCode.getCode();
		this.msg = resultCode.getMsg();
	}

	public String toJSON() {
		return JSON.toJSONString(this);
	}

}
