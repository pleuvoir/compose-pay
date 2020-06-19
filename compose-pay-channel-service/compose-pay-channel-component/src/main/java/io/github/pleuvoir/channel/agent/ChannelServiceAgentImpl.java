package io.github.pleuvoir.channel.agent;

import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.dispatch.ServiceDispatcher;
import io.github.pleuvoir.channel.model.request.*;
import io.github.pleuvoir.channel.model.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通道服务实现
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Component
public class ChannelServiceAgentImpl implements IChannelServiceAgent {

    @Autowired
    private ServiceDispatcher dispatcher;

    @Override
    public PaymentResultDTO payOrder(PaymentDTO paymentDTO) throws ChannelServiceException {
        return dispatcher.doDispatch(paymentDTO);
    }

    @Override
    public PayQueryResultDTO payQuery(PayQueryDTO payQueryDTO) throws ChannelServiceException {
        return dispatcher.doDispatch(payQueryDTO);
    }

    @Override
    public PaymentCloseResultDTO closeOrder(PaymentCloseDTO paymentCloseDTO) throws ChannelServiceException {
        return dispatcher.doDispatch(paymentCloseDTO);
    }

    @Override
    public RefundResultDTO refund(RefundDTO refundDTO) throws ChannelServiceException {
        return dispatcher.doDispatch(refundDTO);
    }

    @Override
    public RefundQueryResultDTO refundQuery(RefundQueryDTO refundQueryDTO) throws ChannelServiceException {
        return dispatcher.doDispatch(refundQueryDTO);
    }

    @Override
    public NotifyParamResultDTO payNotifyReceive(NotifyParamDTO notifyParamDTO) {
        return null;
    }


}
