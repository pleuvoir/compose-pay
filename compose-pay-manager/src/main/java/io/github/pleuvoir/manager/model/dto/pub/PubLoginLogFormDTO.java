package io.github.pleuvoir.manager.model.dto.pub;

import io.github.pleuvoir.manager.model.dto.AbstractFormPageDTO;

/**
 * 登录日志查询form
 * @author cheng
 *
 */
public class PubLoginLogFormDTO extends AbstractFormPageDTO {
	
	private String username;
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
