package io.github.pleuvoir.manager.model.po.pub;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 1.1.7	内管用户登录信息表
 * @author LaoShu
 *
 */
@TableName("pub_user")
public class PubUserPO implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1898779058722928733L;

	/**状态0:未生效	 */
	public static final String STATUS_NOT_ACTIVED = "0";
	/**状态1:已生效	 */
	public static final String STATUS_ACTIVED = "1";
	/**状态2:锁定	 */
	public static final String STATUS_LOCKED = "2";
	/**状态3:删除	 */
	public static final String STATUS_DELETE = "3";
	
	@TableId("id")
	private String id;

	@NotBlank(message = "用户名不能为空")
	@TableField("username")
	private String username;		//用户名
	
	@NotBlank(message = "登录密码不能为空")
	@TableField("password")
	private String password;		//登录密码
		
	@TableField("safe_password")
	private String safePassword;		//交易密码
	
	@TableField("status")
	private String status;
	
	@TableField("create_by")
	private String createBy;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	@TableField("create_time")
	private LocalDateTime createTime;
	
	@TableField("remark")
	private String remark;
	
	@TableField("salt")
	private String salt;
	
	@TableField(exist=false)
	private String oldPass;
	
	@TableField(exist=false)
	private String newPass;

	@TableField(exist=false)
	private String repeatPass;
	
	@TableField(exist = false)
	private String roleNames;	//用户拥有的角色(list页面显示)
	
	@TableField(exist = false)
	private String[] roles;		//添加页面接收选择的角色
	
	@TableField(exist = false)
	private String loginUsername;	//登录用户
	
	
	@TableField(exist=false)
	private String realName;	//姓名
	@TableField(exist=false)
	private String kaptcha;		//验证码
	@TableField(exist=false)
	private String password_1;	//密码密文
	
	
	
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSafePassword() {
		return safePassword;
	}

	public void setSafePassword(String safePassword) {
		this.safePassword = safePassword;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getKaptcha() {
		return kaptcha;
	}

	public void setKaptcha(String kaptcha) {
		this.kaptcha = kaptcha;
	}

	public String getPassword_1() {
		return password_1;
	}

	public void setPassword_1(String password_1) {
		this.password_1 = password_1;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
