package io.github.pleuvoir.manager.model.po.pub;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 1.1.1	菜单表
 * @author LaoShu
 *
 */
@TableName("pub_menu")
public class PubMenuPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2301483597766704925L;
	
	@TableId("id")
	private String id;
	
	@TableField("has_child")
	private String hasChild;
	
	@TableField("icon")
	private String icon;
	
	@TableField("node")
	private String node;
	
	@TableField("parent_id")
	private String parentId;
	
	@TableField("path")
	private String path;
	
	@TableField("sort")
	private String sort;
	
	@TableField("title")
	private String title;
	
	@TableField("is_show")
	private String isShow;
	
	@TableField("remark")
	private String remark;
	
	@TableField(exist=false)
	private String parentTitle;	//父级菜单名称
	
	/**是否有子菜单:是 */
	public static final String HASCHILE_TRUE = "Y";
	/**是否有子菜单:否 */
	public static final String HASCHILE_FALSE = "N";
	
	/**节点类型单:顶级节点 */
	public static final String NODE_ZERO = "0";
	/**节点类型:一级节点 */
	public static final String NODE_ONE = "1";
	/**节点类型:二级节点 */
	public static final String NODE_TWO = "2";
	/**节点类型:三级节点 */
	public static final String NODE_THREE = "3";
	
	/**是否显示:不显示 */
	public static final String IS_SHOW_FALSE = "0";
	/**是否显示:显示 */
	public static final String IS_SHOW_TRUE = "1";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHasChild() {
		return hasChild;
	}

	public void setHasChild(String hasChild) {
		this.hasChild = hasChild;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getParentTitle() {
		return parentTitle;
	}

	public void setParentTitle(String parentTitle) {
		this.parentTitle = parentTitle;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
