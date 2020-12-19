package io.github.pleuvoir.gateway.model.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Data
public class PayRequestResultDTO implements Serializable {


    /**
     * 发起支付需要的参数信息字符串
     */
    private String paramStr;

    /**
     * 商户号
     */
    private String mid;


    /**
     * 交易唯一流水号
     */
    private Long transUniqueId;

    /**
     * 支付系统唯一流水号
     */
    private Long payId;

    /**
     * 支付成功跳转地址
     */
    private String paySuccessUrl;

}
