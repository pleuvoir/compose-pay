package io.github.pleuvoir.channel.core;

import io.github.pleuvoir.channel.common.ChannelEnum;
import io.github.pleuvoir.channel.common.TransEnum;

import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface ChannelServicePlugin extends Plugin {


    /**
     * 获取交易类型和服务类
     *
     * @param channel 通道
     */
    Map<TransEnum, Class<?>> getTransMap(ChannelEnum channel);

    /**
     * 获取服务类名
     *
     * @param channel 通道类型
     * @param trans   交易类型
     */
    Class<?> getServiceName(ChannelEnum channel, TransEnum trans);

    /**
     * 获取所有的渠道
     */
    Set<ChannelEnum> getChannels();

}
