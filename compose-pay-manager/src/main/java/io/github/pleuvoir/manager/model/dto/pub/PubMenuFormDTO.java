package io.github.pleuvoir.manager.model.dto.pub;

import java.util.HashMap;
import java.util.Map;

import io.github.pleuvoir.manager.model.dto.AbstractFormPageDTO;
import io.github.pleuvoir.manager.model.po.pub.PubPermissionPO;

/**
 * 菜单查询form
 * @author LaoShu
 *
 */
public class PubMenuFormDTO extends AbstractFormPageDTO {

	private String id;
	
	private String title;
	
	private String parentId;

	private String node;
	
	private Map<String, PubPermissionPO> perMap = new HashMap<>();	//绑定权限信息

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Map<String, PubPermissionPO> getPerMap() {
		return perMap;
	}

	public void setPerMap(Map<String, PubPermissionPO> perMap) {
		this.perMap = perMap;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}
}
