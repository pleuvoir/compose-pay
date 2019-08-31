package io.github.pleuvoir.manager.model.dto.pub;

import io.github.pleuvoir.manager.model.dto.AbstractFormPageDTO;

/**
 * 角色查询form
 * @author LaoShu
 *
 */
public class PubRoleFormDTO extends AbstractFormPageDTO {

	private String name;
	
	private String loginUserName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

}
