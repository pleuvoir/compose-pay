package io.github.pleuvoir.manager.model.dto.pub;

import io.github.pleuvoir.manager.model.dto.AbstractFormPageDTO;

/**
 * 参数查询form
 * @author abeir
 *
 */
public class PubParamFormDTO extends AbstractFormPageDTO {

	private String code;
	
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
