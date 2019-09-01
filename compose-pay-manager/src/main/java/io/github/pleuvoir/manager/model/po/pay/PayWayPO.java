package io.github.pleuvoir.manager.model.po.pay;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

@Data
@TableName("p_pay_way")
public class PayWayPO {
	
	
	private String id;
	
	private String payWayCode;
	
	private String payWayName;
	
	private String remark;
	

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
