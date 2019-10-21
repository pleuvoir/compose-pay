package io.github.pleuvoir.gateway.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSON;

import lombok.Data;

/**
 * 
 * @author pleuvoir
 * 
 */
@Data
public class PaymentDTO implements Serializable {

	private static final long serialVersionUID = -1699831686561099489L;
	@Length(max = 15)
	@NotEmpty
	private String mid;			//商户号
	@Length(max = 32)
	@NotEmpty
	private String orderNo;		//商户系统中的订单号
	@Length(max = 128)
	@NotEmpty
	private String subject;		//商品名称
	@Length(max = 128)
	@NotEmpty
	private String body;		//描述
	@Digits(integer = 10,fraction = 2)
	@DecimalMin(value = "0.01")
	private BigDecimal amount;		//金额
	
	@Length(max = 16)
	private String type;		//类型：wechat：微信 alipay：支付宝
	
	private String ip;
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
