package io.github.pleuvoir.channel.channels.mock;

import io.github.pleuvoir.channel.channels.AbstractChannelTemplate;
import io.github.pleuvoir.channel.common.annotation.ChannelService;
import io.github.pleuvoir.channel.model.ChannelMerDTO;
import io.github.pleuvoir.channel.model.request.PaymentDTO;
import io.github.pleuvoir.channel.model.response.PaymentResultDTO;
import io.github.pleuvoir.pay.common.enums.ChannelEnum;
import io.github.pleuvoir.pay.common.enums.ServiceTypeEnum;
import java.util.HashMap;
import java.util.Map;

/**
 * 模拟通道
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@ChannelService(channel = ChannelEnum.MOCK, serviceTypeEnum = ServiceTypeEnum.PAY)
public class MockPay extends AbstractChannelTemplate<PaymentDTO, PaymentResultDTO> {

    @Override
    protected void verify(PaymentDTO request) {

    }

    @Override
    protected Map<String, String> generateParams(PaymentDTO paymentDTO, ChannelMerDTO account) {
        return new HashMap<>();
    }

    @Override
    protected Map<String, String> doRequest(Map<String, String> params, ChannelMerDTO account) {
        return new HashMap<>();
    }

    @Override
    protected PaymentResultDTO convertResult(Map<String, String> result, PaymentDTO paymentDTO, ChannelMerDTO account) {
        PaymentResultDTO resultDTO = new PaymentResultDTO();
        resultDTO.setParamStr("http://www.google.com");
        return resultDTO;
    }
}
