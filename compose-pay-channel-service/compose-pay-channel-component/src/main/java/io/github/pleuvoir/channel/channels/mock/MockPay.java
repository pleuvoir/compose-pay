package io.github.pleuvoir.channel.channels.mock;

import com.google.common.collect.Maps;
import io.github.pleuvoir.channel.channels.AbstractChannelTemplate;
import io.github.pleuvoir.channel.channels.IChannelService;
import io.github.pleuvoir.channel.common.annotation.ChannelService;
import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.ChannelMerDTO;
import io.github.pleuvoir.channel.model.request.PaymentDTO;
import io.github.pleuvoir.channel.model.response.PaymentResultDTO;
import io.github.pleuvoir.pay.common.enums.ChannelEnum;
import io.github.pleuvoir.pay.common.enums.ServiceTypeEnum;
import java.util.Map;

/**
 * 模拟通道
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@ChannelService(channel = ChannelEnum.MOCK, serviceTypeEnum = ServiceTypeEnum.PAY)
public class MockPay implements IChannelService<PaymentDTO, PaymentResultDTO> {

    @Override
    public PaymentResultDTO invoke(PaymentDTO request) throws ChannelServiceException {
        return null;
    }
}
