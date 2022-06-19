package io.github.pleuvoir.pay.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 服务类别<br> 此枚举里定义了所有能够提供的服务
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@AllArgsConstructor
public enum ServiceTypeEnum {

    //预下单
    PAY("pay"),
    //支付查询
    PAY_QUERY("pay_query"),
    //退款
    REFUND("refund"),
    //退款查询
    REFUND_QUERY("refund_query"),
    //关闭订单
    CLOSE_ORDER("close_order"),
    //支付通知
    PAY_NOTIFY("pay_notify"),

    ;

    @Getter
    private String id;

    public static ServiceTypeEnum toEnum(String symbol) {
        return ServiceTypeEnum.valueOf(StringUtils.upperCase(StringUtils.strip(symbol)));
    }
}
