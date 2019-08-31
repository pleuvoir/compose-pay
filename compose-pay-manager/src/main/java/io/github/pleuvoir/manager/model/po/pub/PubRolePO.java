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
 * 1.1.3	角色表
 * @author LaoShu
 *
 */
@TableName("pub_role")
public class PubRolePO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5205922258148663296L;

	@TableId("id")
	private String id;
	
	@NotBlank(message = "角色名称不能为空")
	@TableField("name")
	private String name;		//角色名称
	
	@TableField("create_by")
	private String createBy;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	@TableField("create_time")
	private LocalDateTime createTime;
	
	@TableField("is_tmp")
	private String isTmp;		//是否作为模板
	
	@TableField("remark")
	private String remark;
	
	@TableField(exist=false)
	private String strCHeck;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getIsTmp() {
		return isTmp;
	}

	public void setIsTmp(String isTmp) {
		this.isTmp = isTmp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStrCHeck() {
		return strCHeck;
	}

	public void setStrCHeck(String strCHeck) {
		this.strCHeck = strCHeck;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
