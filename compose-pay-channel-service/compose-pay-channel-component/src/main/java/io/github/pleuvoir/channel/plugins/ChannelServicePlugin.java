package io.github.pleuvoir.channel.plugins;

import io.github.pleuvoir.channel.common.ChannelEnum;
import io.github.pleuvoir.channel.common.ServiceIdEnum;

import java.util.Map;
import java.util.Set;

/**
 * 通道服务插件
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface ChannelServicePlugin extends Plugin {


    /**
     * 获取服务插件类别和服务类
     *
     * @param channel 通道
     */
    Map<ServiceIdEnum, Class<?>> getServiceMap(ChannelEnum channel);

    /**
     * 获取服务类名
     *
     * @param channel   通道类型
     * @param serviceId 服务插件类别
     */
    Class<?> getServiceName(ChannelEnum channel, ServiceIdEnum serviceId);

    /**
     * 获取所有的通道
     */
    Set<ChannelEnum> getChannels();

}
