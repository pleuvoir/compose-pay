package io.github.channel.agent;

import io.github.channel.BaseTest;
import io.github.pleuvoir.channel.agent.IStdChannelServiceAgent;
import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.request.PaymentDTO;
import io.github.pleuvoir.channel.model.response.PaymentResultDTO;
import io.github.pleuvoir.pay.common.enums.PayProductEnum;
import java.math.BigDecimal;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class ChannelServiceAgentTest extends BaseTest {


    @Autowired
    private IStdChannelServiceAgent serviceAgent;

    @Test
    public void pay_test() throws ChannelServiceException {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(BigDecimal.ONE);
        paymentDTO.setOrderId(String.valueOf(System.currentTimeMillis()));
        paymentDTO.setOrderId(String.valueOf(System.currentTimeMillis()));
        paymentDTO.setPayProduct(PayProductEnum.MOCK_H5.getCode());
        String mid = String.valueOf(System.currentTimeMillis());

        PaymentResultDTO resultDTO = serviceAgent.payOrder(paymentDTO);

    }
}
