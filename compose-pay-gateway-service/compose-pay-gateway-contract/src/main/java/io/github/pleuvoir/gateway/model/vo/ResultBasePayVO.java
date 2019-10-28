package io.github.pleuvoir.gateway.model.vo;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author pleuvoir
 */
@Data
public class ResultBasePayVO implements Serializable {

    private static final long serialVersionUID = 5566661845252721918L;

    private String orderNo; // 商户系统中的订单号
    private BigDecimal amount; // 金额
    private String type; // 类型：wechat：微信 alipay：支付宝
    private String datetime; // 订单创建时间，yyyyMMddHHmmss
    private String qrCode; // 二维码链接

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
