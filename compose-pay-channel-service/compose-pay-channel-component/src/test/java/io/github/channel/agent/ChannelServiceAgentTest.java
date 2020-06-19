package io.github.channel.agent;

import io.github.channel.BaseTest;
import io.github.pleuvoir.channel.agent.IChannelServiceAgent;
import io.github.pleuvoir.channel.agent.IChannelServiceAgent;
import io.github.pleuvoir.channel.common.ChannelEnum;
import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.request.PaymentDTO;
import io.github.pleuvoir.channel.model.response.PaymentResultDTO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class ChannelServiceAgentTest extends BaseTest {


    @Autowired
    private IChannelServiceAgent serviceAgent;

    @Test
    public void pay() throws ChannelServiceException {

        PaymentDTO paymentDTO = new PaymentDTO();

        paymentDTO.setChannel(ChannelEnum.MOCK);

        String mid = String.valueOf(System.currentTimeMillis());
        paymentDTO.setMid(mid);

        PaymentResultDTO resultDTO = serviceAgent.payOrder(paymentDTO);

        Assert.assertEquals(mid,resultDTO.getMid());
    }
}
