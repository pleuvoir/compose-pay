package io.github.pleuvoir.manager.model.po.pub;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 参数
 * @author abeir
 *
 */
@TableName("pub_param")
public class PubParamPO {
	/** 参数类型：小数 */
	public static final String TYPE_DECIMAL = "1";
	/** 参数类型：整数 */
	public static final String TYPE_INT = "2";
	/** 参数类型：字符串 */
	public static final String TYPE_STRING = "3";
	/** 参数类型：布尔 */
	public static final String TYPE_BOOLEAN = "4";
	
	/** 是否可修改：不允许 */
	public static final String MODIFY_FLAG_FORBID = "0";
	/** 是否可修改：允许 */
	public static final String MODIFY_FLAG_ALLOW = "1";

	/** 临时文件保存路径 */
	public static final String CODE_TEMP_FILE_PATH = "1007";
	/** 图片压缩像素 */
	public static final String CODE_COMPRESS_IMAGE_PIXEL = "1008";
	/** 专场拍品下限 */
	public static final String CODE_LOT_TOTAL_LOWER_LIMIT = "1017";
	/** 专场拍品上限 */
	public static final String CODE_LOT_TOTAL_UPPER_LIMIT = "1018";
	/** 拍卖H5接口访问地址(外网) */
	public static final String CODE_AUCTIONCORE_ADDRESS = "2001";
	/** 文件服务器访问地址(外网) */
	public static final String CODE_FASTDFS_ADDRESS = "2002";
	/** 定时任务访问地址(内网) */
	public static final String CODE_AUCTIONTASK_ADDRESS = "2003";
	
	/** 退货-买家发货时长（小时） */
	public static final String CODE_BUYER_TIME= "3009";
	
	@NotBlank(message = "参数编号不能为空")
	@TableId("code")
	private String code;		//参数编码
	
	@NotBlank(message = "参数名称不能为空")
	@TableField("name")
	private String name;		//参数名称	
	
	@TableField("group_code")
	private String groupCode;		//参数分组编码	

	@TableField("decimal_val")
	private BigDecimal decimalVal;		//decimal类型	
	
	@TableField("int_val")
	private Integer intVal;		//int类型	
	
	@TableField("str_val")
	private String strVal;		//字符串类型	
	
	@TableField("boolean_val")
	private String booleanVal;		//布尔类型
	
	@NotBlank(message = "参数类型不能为空")
	@TableField("type")
	private String type;			//参数类型	1：decimal 2：int 3：string 4：boolean
	
	@NotBlank(message = "修改标志不能为空")
	@TableField("modify_flag")
	private String modifyFlag;		//是否可修改	1：允许；0：不允许
	
	@TableField("remark")
	private String remark;		//备注	

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

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public BigDecimal getDecimalVal() {
		return decimalVal;
	}

	public void setDecimalVal(BigDecimal decimalVal) {
		this.decimalVal = decimalVal;
	}

	public Integer getIntVal() {
		return intVal;
	}

	public void setIntVal(Integer intVal) {
		this.intVal = intVal;
	}

	public String getStrVal() {
		return strVal;
	}

	public void setStrVal(String strVal) {
		this.strVal = strVal;
	}

	public String getBooleanVal() {
		return booleanVal;
	}

	public void setBooleanVal(String booleanVal) {
		this.booleanVal = booleanVal;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getModifyFlag() {
		return modifyFlag;
	}

	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
