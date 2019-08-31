package io.github.pleuvoir.manager.model.vo;

import org.springframework.validation.ObjectError;

/**
 * 用户ajax返回时的消息
 * @author abeir
 *
 */
public class ResultMessageVO<T> {
	
	public static final String SUCCESS = "success";
	
	public static final String ERROR = "error";

	private String status;		//状态
	
	private String message;		//消息
	
	private T data;		//返回的数据
	
	public static <T> ResultMessageVO<T> success(String message) {
		ResultMessageVO<T> msg = new ResultMessageVO<>();
		msg.setStatus(SUCCESS);
		msg.setMessage(message);
		return msg;
	}
	
	public static <T> ResultMessageVO<T> error(String message) {
		ResultMessageVO<T> msg = new ResultMessageVO<>();
		msg.setStatus(ERROR);
		msg.setMessage(message);
		return msg;
	}
	
	public static <T> ResultMessageVO<T> error(ObjectError message) {
		ResultMessageVO<T> msg = new ResultMessageVO<>();
		msg.setStatus(ERROR);
		msg.setMessage(message.getDefaultMessage());
		return msg;
	}
	
	

	public String getStatus() {
		return status;
	}

	public ResultMessageVO<T> setStatus(String status) {
		this.status = status;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public ResultMessageVO<T> setMessage(String message) {
		this.message = message;
		return this;
	}

	public T getData() {
		return data;
	}

	public ResultMessageVO<T> setData(T data) {
		this.data = data;
		return this;
	}
}
