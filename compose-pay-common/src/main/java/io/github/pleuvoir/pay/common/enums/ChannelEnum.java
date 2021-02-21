package io.github.pleuvoir.pay.common.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 通道枚举
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public enum ChannelEnum {

    WECHAT(1),
    ALIPAY(2),
    MOCK(3);

    @Getter
    private Integer code;

    ChannelEnum(Integer code) {
        this.code = code;
    }

    /**
     * 获取通道
     *
     * @param code 通道编号
     */
    public static ChannelEnum toEnum(Integer code) {
        for (ChannelEnum item : ChannelEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 获取通道
     *
     * @param channel 类型的字符串（忽略大小写，剔除空格）
     */
    public static ChannelEnum toEnum(String channel) {
        return ChannelEnum.valueOf(StringUtils.upperCase(StringUtils.trim(channel)));
    }
}
