package io.github.pleuvoir.channel.common;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 通道枚举
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public enum ChannelEnum {

    MOCK(0),
    WECHAT(1),
    ALIPAY(2);

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
    public static ChannelEnum toEnumByCode(Integer code) {
        for (ChannelEnum item : ChannelEnum.values()) {
            if (item.getCode() == code) {
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
    public static ChannelEnum toEnumByName(String channel) {
        return ChannelEnum.valueOf(StringUtils.upperCase(StringUtils.trim(channel)));
    }
}
