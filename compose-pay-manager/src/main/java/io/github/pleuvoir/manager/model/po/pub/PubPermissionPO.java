package io.github.pleuvoir.manager.model.po.pub;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 1.1.2	菜单权限表
 * @author LaoShu
 *
 */
@TableName("pub_permission")
public class PubPermissionPO implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 96073437217038779L;

	@TableId("id")
	private String id;
	
	@TableField("name")
	private String name;		//权限名称

	@TableField("code")
	private String code;		//编号 控制器名:访问功能名组成
	
	@TableField("menu_id")
	private String menuId;		//菜单ID
	
	@TableField("remark")
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
