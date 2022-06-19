package io.github.pleuvoir.channel.factory;

import io.github.pleuvoir.channel.channels.IChannelService;
import io.github.pleuvoir.pay.common.enums.ChannelEnum;
import io.github.pleuvoir.pay.common.enums.ServiceTypeEnum;

/**
 * 通道服务工厂
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public interface IChannelServiceFactory {

    /**
     * 通过通道和服务类别获取通道服务，未获取到时返回null
     */
    IChannelService getChannelService(ChannelEnum channel, ServiceTypeEnum serviceType);
}
