package io.github.pleuvoir.channel.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 服务插件类别
 *
 * <p>该类的主要作用是配合服务插件使用，使每一个请求三方通道的接口都独立为一个单独的类。
 * 对大部分通道而言：虽然有多种支付场景，如H5、扫码、刷卡等，它们可能使用的是一个统一下单
 * 接口。但我们设计的目标依然是把它当做多个接口来处理。
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@AllArgsConstructor
public enum ServiceIdEnum {

    //扫码支付
    SCAN_CODE("scan_code"),
    //APP支付
    APP("app"),
    //付款码支付
    BRUSH_CARD("brush_card"),
    //公众号支付
    GZ("gz"),
    //小程序支付
    MINI_PROGRAM("mini_program"),
    //手机网站支付
    MOBILE_H5("mobile_h5"),
    //电脑网站支付
    PC_WEB("pc_web"),

    //支付查询
    PAY_QUERY("pay_query"),
    //退款查询
    REFUND_QUERY("refund_query"),
    //支付通知
    PAY_NOTIFY("pay_notify"),


    ;

    @Getter
    private String id;

    public static ServiceIdEnum toEnum(String id) {
        if (id == null)
            return null;
        return ServiceIdEnum.valueOf(StringUtils.upperCase(StringUtils.strip(id)));
    }
}
