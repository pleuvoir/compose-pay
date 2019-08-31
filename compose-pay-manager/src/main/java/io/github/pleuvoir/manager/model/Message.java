package io.github.pleuvoir.manager.model;

public class Message {

	public static final String SUCCESS = "success";
	public static final String ERROR = "error";

	private String message = "";
	private String status = ERROR;

	public Message() {
	}

	public Message(String message, String status) {
		this.message = message;
		this.status = status;
	}

	public Message set(String message, String status) {
		this.message = message;
		this.status = status;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Message setMessage(String message) {
		this.message = message;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public Message setStatus(String status) {
		this.status = status;
		return this;
	}
	
	/**
	 * 判断当前的msg对象状态是否为错误
	 * @return 是否为错误
	 */
	public boolean isError(){
		return Message.ERROR.equals(this.getStatus());
	}
}
