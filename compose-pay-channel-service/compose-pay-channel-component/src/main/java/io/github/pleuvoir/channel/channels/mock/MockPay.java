package io.github.pleuvoir.channel.channels.mock;

import com.google.common.collect.Maps;
import io.github.pleuvoir.channel.channels.AbstractChannelTemplate;
import io.github.pleuvoir.channel.model.ChannelMerDTO;
import io.github.pleuvoir.channel.model.request.PaymentDTO;
import io.github.pleuvoir.channel.model.response.PaymentResultDTO;
import java.util.Map;

/**
 * 模拟通道
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class MockPay extends AbstractChannelTemplate<PaymentDTO, PaymentResultDTO> {

    @Override
    protected void verify(PaymentDTO request) {

    }

    @Override
    protected Map<String, String> generateParams(PaymentDTO paymentDTO, ChannelMerDTO account) {
        Map<String, String> params = Maps.newHashMap();
        params.put("orderId", paymentDTO.getOrderId());
        params.put("amount", paymentDTO.getAmount().stripTrailingZeros().toPlainString());
        return params;
    }

    @Override
    protected Map<String, String> doRequest(Map<String, String> params, ChannelMerDTO account) {
        return null;
    }

    @Override
    protected PaymentResultDTO convertResult(Map<String, String> result, PaymentDTO paymentDTO, ChannelMerDTO account) {
        PaymentResultDTO resultDTO = new PaymentResultDTO();
        resultDTO.setParamStr("");
        return resultDTO;
    }
}
