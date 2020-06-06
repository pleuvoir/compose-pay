package io.github.pleuvoir.gateway.model.dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 支付入参
 *
 * @author pleuvoir
 */
@Data
public class PaymentDTO implements Serializable {

    private static final long serialVersionUID = -1699831686561099489L;

    @Length(max = 15)
    @NotBlank(message = "商户号不能为空")
    private String mid;            //商户号

    @Length(max = 32)
    @NotBlank(message = "商户订单号不能为空")
    private String orderNo;        //商户系统中的订单号

    @Length(max = 128)
    @NotBlank(message = "商品名称不能为空")
    private String subject;        //商品名称

    @Length(max = 128)
    @NotBlank(message = "描述不能为空")
    private String body;        //描述

    @Digits(integer = 10, fraction = 2)
    @DecimalMin(value = "0.01", message = "金额必须大于或等于0.01")
    private BigDecimal amount;        //金额

    @Length(max = 16)
    private String type;        //类型：wechat：微信 alipay：支付宝

    private String ip;

    @Length(max = 256)
    @NotEmpty(message = "商户通知地址不能为空")
    private String notifyUrl;	//商户通知地址


}
