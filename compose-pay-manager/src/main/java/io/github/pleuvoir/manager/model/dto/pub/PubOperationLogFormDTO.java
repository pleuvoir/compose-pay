package io.github.pleuvoir.manager.model.dto.pub;

import org.springframework.format.annotation.DateTimeFormat;

import io.github.pleuvoir.manager.model.dto.AbstractFormPageDTO;

import java.util.Date;

/**
 * 操作日志查询表单
 * @author abeir
 *
 */
public class PubOperationLogFormDTO extends AbstractFormPageDTO {

	private String username;
	
	private String status;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
