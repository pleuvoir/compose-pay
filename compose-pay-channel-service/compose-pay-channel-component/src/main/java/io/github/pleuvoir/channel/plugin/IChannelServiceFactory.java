package io.github.pleuvoir.channel.plugin;

import io.github.pleuvoir.channel.common.ChannelEnum;
import io.github.pleuvoir.channel.common.ServiceIdEnum;

/**
 * 通道服务工厂
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface IChannelServiceFactory {

    /**
     * 通过通道和服务类别获取通道服务，未获取到时返回null
     */
    IChannelService getChannelService(ChannelEnum channel, ServiceIdEnum serviceId);
}
