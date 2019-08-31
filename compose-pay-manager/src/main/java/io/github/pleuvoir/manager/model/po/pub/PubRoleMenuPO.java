package io.github.pleuvoir.manager.model.po.pub;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 1.1.5	角色菜单关联表
 * @author LaoShu
 *
 */
@TableName("pub_role_menu")
public class PubRoleMenuPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4310892837563877195L;
	
	@TableField("role_id")
	private String roleId;
	
	@TableField("menu_id")
	private String menuId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
