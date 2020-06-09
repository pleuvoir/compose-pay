package io.github.pleuvoir.channel.extension;

import io.github.pleuvoir.channel.common.ChannelEnum;
import io.github.pleuvoir.channel.common.TransEnum;

/**
 * 通道服务工厂<br>
 * 通过通道编号和交易类型获取实现类
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface IChannelServiceFactory {

    IChannelService getChannelService(ChannelEnum channel, TransEnum trans);
}
