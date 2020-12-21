package io.github.pleuvoir.channel.agent;

import com.alibaba.dubbo.config.annotation.Service;
import io.github.pleuvoir.channel.dispatch.ServiceDispatcher;
import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.request.PayQueryDTO;
import io.github.pleuvoir.channel.model.request.PaymentCloseDTO;
import io.github.pleuvoir.channel.model.request.PaymentDTO;
import io.github.pleuvoir.channel.model.request.RefundDTO;
import io.github.pleuvoir.channel.model.request.RefundQueryDTO;
import io.github.pleuvoir.channel.model.response.PayQueryResultDTO;
import io.github.pleuvoir.channel.model.response.PaymentCloseResultDTO;
import io.github.pleuvoir.channel.model.response.PaymentResultDTO;
import io.github.pleuvoir.channel.model.response.RefundQueryResultDTO;
import io.github.pleuvoir.channel.model.response.RefundResultDTO;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.stereotype.Component;

/**
 * 标准通道服务实现
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Component
@Service
public class StdChannelServiceAgentImpl implements IStdChannelServiceAgent {

    @Resource
    private ServiceDispatcher dispatcher;

    @Override
    public PaymentResultDTO payOrder(@Valid PaymentDTO paymentDTO) throws ChannelServiceException {
        return dispatcher.doDispatch(paymentDTO);
    }

    @Override
    public PayQueryResultDTO payQuery(@Valid PayQueryDTO payQueryDTO) throws ChannelServiceException {
        return dispatcher.doDispatch(payQueryDTO);
    }

    @Override
    public PaymentCloseResultDTO closeOrder(@Valid PaymentCloseDTO paymentCloseDTO) throws ChannelServiceException {
        return dispatcher.doDispatch(paymentCloseDTO);
    }

    @Override
    public RefundResultDTO refund(@Valid RefundDTO refundDTO) throws ChannelServiceException {
        return dispatcher.doDispatch(refundDTO);
    }

    @Override
    public RefundQueryResultDTO refundQuery(@Valid RefundQueryDTO refundQueryDTO) throws ChannelServiceException {
        return dispatcher.doDispatch(refundQueryDTO);
    }

}
