package io.github.pleuvoir.gateway.exception;

import io.github.pleuvoir.gateway.constants.RspCodeEnum;
import lombok.Getter;

public class BusinessException extends Exception {
	
	private static final long serialVersionUID = -121219158129626814L;

	@Getter
	private RspCodeEnum resultCode;
	@Getter
	private String msg;

	public BusinessException() {
	}

	public BusinessException(RspCodeEnum rsCode) {
		super(rsCode.getCode() + ":" + rsCode.getMsg());
		this.resultCode = rsCode;
		this.msg = rsCode.getMsg();
	}

	public BusinessException(RspCodeEnum rsCode, String message) {
		super(rsCode.getCode() + ":" + message);
		this.resultCode = rsCode;
		this.msg = message;
	}

	public BusinessException(RspCodeEnum rsCode, Throwable cause) {
		super(rsCode.getCode() + ":" + rsCode.getMsg(), cause);
		this.resultCode = rsCode;
		this.msg = rsCode.getMsg();
	}

	public BusinessException(RspCodeEnum rsCode, String message, Throwable cause) {
		super(rsCode.getCode() + ":" + message, cause);
		this.resultCode = rsCode;
		this.msg = message;
	}

}
