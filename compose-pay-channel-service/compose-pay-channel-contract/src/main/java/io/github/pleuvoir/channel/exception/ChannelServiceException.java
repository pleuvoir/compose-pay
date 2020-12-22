package io.github.pleuvoir.channel.exception;


import io.github.pleuvoir.pay.common.enums.ResultCodeEnum;

public class ChannelServiceException extends Exception {

	private static final long serialVersionUID = 101408954677206084L;

	private Integer code;
	private String msg;
	
	public ChannelServiceException(){}

	public ChannelServiceException(Integer code, String message) {
		super(code + ":" + message);
		this.code = code;
		this.msg = message;
	}
	
	public ChannelServiceException(Integer code, String message, Throwable cause) {
		super(code + ":" + message, cause);
		this.code = code;
		this.msg = message;
	}

	public ChannelServiceException(ResultCodeEnum rsCode){
		super(rsCode.getCode() + ":" + rsCode.getMsg());
		this.code = rsCode.getCode();
		this.msg = rsCode.getMsg();
	}
	
	public ChannelServiceException(ResultCodeEnum rsCode, String message){
		super(rsCode.getCode() + ":" + message);
		this.code = rsCode.getCode();
		this.msg = message;
	}
	
	public ChannelServiceException(ResultCodeEnum rsCode, Throwable cause) {
		super(rsCode.getCode() + ":" + rsCode.getMsg(), cause);
		this.code = rsCode.getCode();
		this.msg = rsCode.getMsg();
	}
	
	public ChannelServiceException(ResultCodeEnum rsCode, String message, Throwable cause) {
		super(rsCode.getCode() + ":" + message, cause);
		this.code = rsCode.getCode();
		this.msg = message;
	}

	public Integer getCode(){
		return this.code;
	}
	
	public String getMsg(){
		return this.msg;
	}
}
