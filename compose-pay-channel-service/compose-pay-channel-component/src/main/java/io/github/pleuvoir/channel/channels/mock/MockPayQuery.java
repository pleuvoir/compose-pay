package io.github.pleuvoir.channel.channels.mock;

import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.request.PayQueryDTO;
import io.github.pleuvoir.channel.model.response.PayQueryResultDTO;
import io.github.pleuvoir.channel.plugin.IChannelService;

/**
 * 模拟支付结果查询
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class MockPayQuery implements IChannelService<PayQueryDTO, PayQueryResultDTO> {

    @Override
    public PayQueryResultDTO invoke(PayQueryDTO request) throws ChannelServiceException {
        return null;
    }
}
