package io.github.pleuvoir.manager.common.taglib;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

/**
 * 输出bootstrap alert标签
 * @author abeir
 *
 */
public class AlertTag extends RequestContextAwareTag {
	private static final long serialVersionUID = -2581728404196695316L;
	
	private static Logger logger = LoggerFactory.getLogger(AlertTag.class);

	private AlertMessage message; 	//消息内容	支持String和ObjectError
	
	@Override
	protected int doStartTagInternal() throws Exception {
		if(message==null) {
			return 0;
		}
		
		String tag = StringUtils.EMPTY;
		if(message.isSuccess()) {
			tag = alertSuccessTag(message);
		}else if(message.isError()) {
			tag = alertErrorTag(message);
		}else if(message.isInfo()) {
			tag = alertInfoTag(message);
		}else if(message.isWarning()) {
			tag = alertWarnTag(message);
		}
		pageContext.getOut().write(tag);
		return 0;
	}

	/**
	 * 生成错误信息标签
	 */
	private String alertErrorTag(AlertMessage msg) {
		String alertMsg = StringUtils.EMPTY;
		if(msg.getMessage() instanceof String) {
			alertMsg = (String) msg.getMessage();
		}
		if(msg.getMessage() instanceof ObjectError) {
			ObjectError err = (ObjectError)msg.getMessage();
			alertMsg = err.getDefaultMessage();
		}
		if(StringUtils.isBlank(alertMsg)) {
			logger.warn("未传入有效的消息，status：{}，message：{}", msg.getStatus(), msg.getMessage());
			return StringUtils.EMPTY;
		}
		return createTag("danger", alertMsg);
	}
	
	/**
	 * 生成成功信息标签
	 */
	private String alertSuccessTag(AlertMessage msg) {
		String alertMsg = StringUtils.EMPTY;
		if(msg.getMessage() instanceof String) {
			alertMsg = (String)msg.getMessage();
		}
		if(StringUtils.isBlank(alertMsg)) {
			logger.warn("未传入有效的消息，status：{}，message：{}", msg.getStatus(), msg.getMessage());
			return StringUtils.EMPTY;
		}
		return createTag("success", alertMsg);
	}
	
	/**
	 * 生成提醒消息标签 
	 */
	private String alertInfoTag(AlertMessage msg) {
		String alertMsg = StringUtils.EMPTY;
		if(msg.getMessage() instanceof String) {
			alertMsg = (String) msg.getMessage();
		}
		if(StringUtils.isBlank(alertMsg)) {
			logger.warn("未传入有效的消息，status：{}，message：{}", msg.getStatus(), msg.getMessage());
			return StringUtils.EMPTY;
		}
		return createTag("info", alertMsg);
	}
	/**
	 * 生成警告消息标签
	 */
	private String alertWarnTag(AlertMessage msg) {
		String alertMsg = StringUtils.EMPTY;
		if(msg.getMessage() instanceof String) {
			alertMsg = (String) msg.getMessage();
		}
		if(StringUtils.isBlank(alertMsg)) {
			logger.warn("未传入有效的消息，status：{}，message：{}", msg.getStatus(), msg.getMessage());
			return StringUtils.EMPTY;
		}
		return createTag("warning", alertMsg);
	} 

	
	private String createTag(String type, String alertMsg) {
		StringBuilder b = new StringBuilder()
				.append("<div class='alert alert-").append(type).append(" msg-alert-tag'>")
				.append(alertMsg)
				.append("</div>");
		return b.toString();
	}
	

	public AlertMessage getMessage() {
		return message;
	}

	/**
	 * 设置消息
	 * @param message
	 */
	public void setMessage(AlertMessage message) {
		this.message = message;
	}


}
