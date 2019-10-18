package io.github.pleuvoir.gateway.exception;

import io.github.pleuvoir.gateway.constants.ResultCodeEnum;
import lombok.Getter;

public class BusinessException extends Exception {
	
	private static final long serialVersionUID = -121219158129626814L;

	@Getter
	private ResultCodeEnum resultCode;
	@Getter
	private String msg;

	public BusinessException() {
	}

	public BusinessException(ResultCodeEnum rsCode) {
		super(rsCode.getCode() + ":" + rsCode.getMsg());
		this.resultCode = rsCode;
		this.msg = rsCode.getMsg();
	}

	public BusinessException(ResultCodeEnum rsCode, String message) {
		super(rsCode.getCode() + ":" + message);
		this.resultCode = rsCode;
		this.msg = message;
	}

	public BusinessException(ResultCodeEnum rsCode, Throwable cause) {
		super(rsCode.getCode() + ":" + rsCode.getMsg(), cause);
		this.resultCode = rsCode;
		this.msg = rsCode.getMsg();
	}

	public BusinessException(ResultCodeEnum rsCode, String message, Throwable cause) {
		super(rsCode.getCode() + ":" + message, cause);
		this.resultCode = rsCode;
		this.msg = message;
	}

}
