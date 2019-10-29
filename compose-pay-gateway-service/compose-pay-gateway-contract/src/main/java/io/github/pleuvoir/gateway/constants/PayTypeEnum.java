package io.github.pleuvoir.gateway.constants;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 支付种类
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public enum PayTypeEnum {

    /**
     * 支付宝支付
     */
    TYPE_ALIPAY("01", "alipay"),

    /**
     * 微信支付
     */
    TYPE_WECHAT("02", "wechat"),

    ;

    @Getter
    private String code;    //编号，数据库中存储的值

    @Getter
    private String name;    //支付种类名称

    PayTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }


    /**
     * 获取支付种类
     *
     * @param name 支付种类名称
     */
    public static PayTypeEnum getThisByName(String name) {
        PayTypeEnum[] payTypeEnums = PayTypeEnum.values();
        for (PayTypeEnum value : payTypeEnums) {
            if (StringUtils.equalsIgnoreCase(value.getName(), name)) {
                return value;
            }
        }
        return null;
    }

}
