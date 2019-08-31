package io.github.pleuvoir.manager.common.taglib;

import java.io.Serializable;

import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 与标签AlertTag搭配使用，用于生成页面的alert信息
 * @author abeir
 *
 */
public class AlertMessage implements Serializable{
	private static final long serialVersionUID = -4280697869792073494L;
	
	/** 消息类型：成功 */
	public static final String SUCCESS = "success";
	/** 消息类型：失败 */
	public static final String ERROR = "error";
	/** 消息类型：提醒 */
	public static final String INFO = "info";
	/** 消息类型：警告 */
	public static final String WARN = "warn";
	
	/** JSP中标签的参数名，默认为message */
	public static final String ALERT_TAG_ATTRIBUTE = "message";

	private String status;
	
	private Object message;
	
	//对应jsp标签中的参数名
	private String alertTagAttribute = ALERT_TAG_ATTRIBUTE;
	
	public AlertMessage() {}
	
	public AlertMessage(String status, Object message) {
		this.status = status;
		this.message = message;
	}
	
	/**
	 * 创建成功消息
	 * @param message 消息内容
	 * @return
	 */
	public static AlertMessage success(String message) {
		return new AlertMessage(SUCCESS, message);
	}
	/**
	 * 创建失败消息
	 * @param message 消息内容
	 * @return
	 */
	public static AlertMessage error(String message) {
		return new AlertMessage(ERROR, message);
	}
	/**
	 * 创建参数校验失败消息
	 * @param message 参数错误信息 
	 * @return
	 */
	public static AlertMessage error(ObjectError message) {
		return new AlertMessage(ERROR, message);
	}
	
	public boolean isSuccess() {
		return SUCCESS.equals(status);
	}
	
	public boolean isError() {
		return ERROR.equals(status);
	}
	
	public boolean isInfo() {
		return INFO.equals(status);
	}
	
	public boolean isWarning() {
		return WARN.equals(status);
	}

	public String getStatus() {
		return status;
	}

	public AlertMessage setStatus(String status) {
		this.status = status;
		return this;
	}

	public Object getMessage() {
		return message;
	}

	public AlertMessage setMessage(Object message) {
		this.message = message;
		return this;
	}

	/**
	 * 直接设置flash消息
	 * @param ra
	 */
	public AlertMessage flashAttribute(RedirectAttributes ra) {
		ra.addFlashAttribute(alertTagAttribute, this);
		return this;
	}
	
	/**
	 * 设置对应jsp标签中的参数名
	 * @param alertTagAttribute
	 */
	public AlertMessage setAlertTagAttribute(String alertTagAttribute) {
		this.alertTagAttribute = alertTagAttribute;
		return this;
	}
}
