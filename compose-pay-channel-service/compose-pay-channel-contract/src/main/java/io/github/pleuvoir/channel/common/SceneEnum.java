package io.github.pleuvoir.channel.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 支付场景枚举
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@AllArgsConstructor
@NoArgsConstructor
public enum SceneEnum {

    PAY_QUERY("1", "支付结果查询"),
    PAY("2", "预下单"),

    ;

    @Getter
    private String code;
    @Getter
    private String message;


    /**
     * 将字符串转换成枚举，若字符串对应的枚举值不存在，则返回null
     */
    public static SceneEnum toEnum(String value) {
        if (value == null)
            return null;
        return SceneEnum.valueOf(StringUtils.upperCase(StringUtils.strip(value)));
    }
}
