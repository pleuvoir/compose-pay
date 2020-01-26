package io.github.pleuvoir.channel.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 渠道
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public enum ChannelEnum {

    WECHAT,
    ALIPAY,
    TEST;


    /**
     * 获取渠道
     *
     * @param channel 类型的字符串（忽略大小写，剔除空格）
     */
    public static ChannelEnum toEnum(String channel) {
        return ChannelEnum.valueOf(StringUtils.upperCase(StringUtils.trim(channel)));
    }
}
