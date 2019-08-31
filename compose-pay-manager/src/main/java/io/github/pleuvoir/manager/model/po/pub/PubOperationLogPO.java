package io.github.pleuvoir.manager.model.po.pub;

import java.time.LocalDateTime;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 操作日志
 * @author abeir
 *
 */
@TableName("pub_operation_log")
public class PubOperationLogPO {
	/** 状态：成功 */
	public static final String STATUS_SUCCESS = "1";
	/** 状态：失败 */
	public static final String STATUS_ERROR = "2";

	@TableId("id")
	private String id;
	
	@TableField("username")
	private String username;		//用户名
	
	@TableField("menu_id")
	private String menuId;		//菜单ID
	
	@TableField("permission_id")
	private String permissionId;		//权限ID
	
	@TableField("controller")
	private String controller;		//controller的类名
	
	@TableField("method")
	private String method;		//controller的方法名
	
	@TableField("status")
	private String status;		//执行状态
	
	@TableField("ip")
	private String ip;			//操作用户IP
	
	@TableField("elapsed_time")
	private Long elapsedTime;		//耗时	毫秒
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	@TableField("create_time")
	private LocalDateTime createTime;		//创建时间
	
	@TableField("remark")
	private String remark;			//备注
	
	@TableField(exist=false)
	private String menuName;		//菜单名称
	
	@TableField(exist=false)
	private String permissionName;		//权限名称
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Long getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(Long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
}
