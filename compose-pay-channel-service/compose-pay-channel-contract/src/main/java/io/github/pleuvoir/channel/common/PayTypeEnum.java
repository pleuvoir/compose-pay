package io.github.pleuvoir.channel.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 支付种类枚举
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@AllArgsConstructor
@NoArgsConstructor
public enum PayTypeEnum {

    /* 微信 */
    WECHAT("01"),
    /* 支付宝 */
    ALIPAY("02"),

    ;

    @Getter
    private String code;

    /**
     * 根据支付种类编码获取支付种类，没找到则返回null
     */
    public static PayTypeEnum toEnum(String code) {
        if (StringUtils.isBlank(code)) return null;
        for (PayTypeEnum item : PayTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
