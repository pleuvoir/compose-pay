package io.github.pleuvoir.channel.exception;

import io.github.pleuvoir.channel.common.RspCodeEnum;

public class ChannelException extends Exception {

	private static final long serialVersionUID = 101408954677206084L;

	private String code;
	private String msg;
	
	public ChannelException(){}

	public ChannelException(String code, String message) {
		super(code + ":" + message);
		this.code = code;
		this.msg = message;
	}
	
	public ChannelException(String code, String message, Throwable cause) {
		super(code + ":" + message, cause);
		this.code = code;
		this.msg = message;
	}

	public ChannelException(RspCodeEnum rsCode){
		super(rsCode.getCode() + ":" + rsCode.getMsg());
		this.code = rsCode.getCode();
		this.msg = rsCode.getMsg();
	}
	
	public ChannelException(RspCodeEnum rsCode, String message){
		super(rsCode.getCode() + ":" + message);
		this.code = rsCode.getCode();
		this.msg = message;
	}
	
	public ChannelException(RspCodeEnum rsCode, Throwable cause) {
		super(rsCode.getCode() + ":" + rsCode.getMsg(), cause);
		this.code = rsCode.getCode();
		this.msg = rsCode.getMsg();
	}
	
	public ChannelException(RspCodeEnum rsCode, String message, Throwable cause) {
		super(rsCode.getCode() + ":" + message, cause);
		this.code = rsCode.getCode();
		this.msg = message;
	}

	public String getCode(){
		return this.code;
	}
	
	public String getMsg(){
		return this.msg;
	}
}
