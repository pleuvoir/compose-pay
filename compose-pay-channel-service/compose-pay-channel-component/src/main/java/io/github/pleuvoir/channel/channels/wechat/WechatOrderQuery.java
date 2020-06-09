package io.github.pleuvoir.channel.channels.wechat;

import io.github.pleuvoir.channel.extension.IChannelService;
import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.request.OrderQueryDTO;
import io.github.pleuvoir.channel.model.response.OrderQueryResultDTO;

/**
 * 微信订单查询
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class WechatOrderQuery implements IChannelService<OrderQueryDTO, OrderQueryResultDTO> {

    @Override
    public OrderQueryResultDTO invoke(OrderQueryDTO request) throws ChannelServiceException {
        return null;
    }
}
