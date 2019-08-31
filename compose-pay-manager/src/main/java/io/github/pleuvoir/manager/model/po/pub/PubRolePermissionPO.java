package io.github.pleuvoir.manager.model.po.pub;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 1.1.4	角色权限表
 * @author LaoShu
 *
 */
@TableName("pub_role_permission")
public class PubRolePermissionPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -712269131427968501L;
	
	@TableField("role_id")
	private String roleId;
	
	@TableField("permission_id")
	private String permissionId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
}
