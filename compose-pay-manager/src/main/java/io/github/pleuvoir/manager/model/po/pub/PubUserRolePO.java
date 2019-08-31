package io.github.pleuvoir.manager.model.po.pub;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 1.1.6	用户角色关联表
 * @author LaoShu
 *
 */
@TableName("pub_user_role")
public class PubUserRolePO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1997426910452059357L;
	
	@TableField("user_id")
	private String userId;
	
	@TableField("role_id")
	private String roleId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
}
