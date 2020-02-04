package io.github.pleuvoir.channel.common;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public enum TransEnum {

    PAY_QUERY("支付结果查询"),
    PAY("预下单"),

    ;

    @Getter
    private String message;

    TransEnum(String message) {
        this.message = message;
    }

    /**
     * 将字符串转换成枚举，若字符串对应的枚举值不存在，则返回null
     */
    public static TransEnum toEnum(String value) {
        value = StringUtils.trim(value);
        value = StringUtils.upperCase(value);
        if (value == null)
            return null;
        return TransEnum.valueOf(value);
    }
}
