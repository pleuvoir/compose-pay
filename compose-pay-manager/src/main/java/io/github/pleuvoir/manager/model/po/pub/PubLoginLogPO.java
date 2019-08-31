package io.github.pleuvoir.manager.model.po.pub;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 1.1.8	登录日志
 * @author LaoShu
 *
 */
@TableName("pub_login_log")
public class PubLoginLogPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -773998144522621695L;
	
	/**
	 * 登录成功
	 */
	public static final String STATUS_SUCCESS = "0";
	/**
	 * 登录失败
	 */
	public static final String STATUS_FAIL = "1";
	
	@TableId("id")
	private String id;
	
	@TableField("username")
	private String username;
	
	@TableField("agent")
	private String agent;
	
	@TableField("ip")
	private String ip;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	@TableField("login_date")
	private LocalDateTime loginDate;
	
	@TableField("status")
	private String status;
	
	@TableField("remark")
	private String remark;

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

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}


	public LocalDateTime getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(LocalDateTime loginDate) {
		this.loginDate = loginDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
