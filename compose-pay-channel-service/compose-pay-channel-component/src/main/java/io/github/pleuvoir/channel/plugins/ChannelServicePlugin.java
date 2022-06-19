package io.github.pleuvoir.channel.plugins;


import io.github.pleuvoir.pay.common.enums.ChannelEnum;
import io.github.pleuvoir.pay.common.enums.ServiceTypeEnum;
import java.util.Map;
import java.util.Set;

/**
 * 通道服务插件
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public interface ChannelServicePlugin extends Plugin {

    /**
     * 获取服务插件类别和服务类
     *
     * @param channel 通道
     */
    Map<ServiceTypeEnum, Class<?>> getServiceMap(ChannelEnum channel);

    /**
     * 获取服务类名
     *
     * @param channel   通道
     * @param serviceType 服务类别
     */
    Class<?> getServiceName(ChannelEnum channel, ServiceTypeEnum serviceType);

    /**
     * 获取所有的通道
     */
    Set<ChannelEnum> getChannels();

}
