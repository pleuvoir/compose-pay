package io.github.pleuvoir.manager.model.dto.pub;

import javax.validation.constraints.NotBlank;

/**
 * 修改登录密码
 * @author LaoShu
 *
 */
public class PubLoginPwdDTO {
	
	@NotBlank(message="原密码不能为空")
	private String oldPass;
	
	@NotBlank(message="新密码不能为空")
	private String newPass;
	
	@NotBlank(message="重新输入密码不能为空")
	private String repeatPass;

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getRepeatPass() {
		return repeatPass;
	}

	public void setRepeatPass(String repeatPass) {
		this.repeatPass = repeatPass;
	}
	
}
