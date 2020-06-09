package io.github.pleuvoir.channel.channels.mock;

import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.extension.IChannelService;
import io.github.pleuvoir.channel.model.ChannelMerDTO;
import io.github.pleuvoir.channel.model.request.OrderQueryDTO;
import io.github.pleuvoir.channel.model.response.OrderQueryResultDTO;

/**
 * 模拟通道订单查询
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class MockOrderQuery implements IChannelService<OrderQueryDTO, OrderQueryResultDTO> {

    @Override
    public OrderQueryResultDTO invoke(OrderQueryDTO request) throws ChannelServiceException {

        OrderQueryResultDTO queryResultDTO = new OrderQueryResultDTO();
        queryResultDTO.setMid(request.getMid());

        return queryResultDTO;
    }
}
