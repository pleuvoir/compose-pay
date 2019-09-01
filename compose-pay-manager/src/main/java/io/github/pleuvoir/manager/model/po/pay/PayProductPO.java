package io.github.pleuvoir.manager.model.po.pay;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

@Data
@TableName("p_pay_product")
public class PayProductPO {
	
	
	private String id;
	
	private String payTypeCode;
	
	private String payWayCode;
	
	private String name;
	
	private String status;
	
	private String remark;
	

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
