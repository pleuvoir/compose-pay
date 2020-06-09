package io.github.pleuvoir.channel.agent;

import io.github.pleuvoir.channel.common.RspCodeEnum;
import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.extension.IChannelService;
import io.github.pleuvoir.channel.extension.IChannelServiceFactory;
import io.github.pleuvoir.channel.model.request.*;
import io.github.pleuvoir.channel.model.response.*;
import io.github.pleuvoir.channel.model.shared.AbstractReqModel;
import io.github.pleuvoir.channel.model.shared.AbstractRspModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 渠道服务实现
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Component
public class ChannelServiceAgentImpl implements ChannelServiceAgent {

    @Autowired
    private IChannelServiceFactory channelServiceFactory;

    @Override
    public PaymentResultDTO payOrder(PaymentDTO paymentDTO) throws ChannelServiceException {

        return this.proxy(paymentDTO);
    }

    @Override
    public OrderQueryResultDTO orderQuery(OrderQueryDTO orderQueryDTO) throws ChannelServiceException {
        return this.proxy(orderQueryDTO);
    }

    @Override
    public PaymentCloseResultDTO closeOrder(PaymentCloseDTO paymentCloseDTO) throws ChannelServiceException {
        return this.proxy(paymentCloseDTO);
    }

    @Override
    public RefundResultDTO refund(RefundDTO refundDTO) throws ChannelServiceException {
        return this.proxy(refundDTO);
    }

    @Override
    public RefundQueryResultDTO refundQuery(RefundQueryDTO refundQueryDTO) throws ChannelServiceException {
        return this.proxy(refundQueryDTO);
    }

    @Override
    public NotifyParamResultDTO payNotifyReceive(NotifyParamDTO notifyParamDTO) {
        return null;
    }


    private <R extends AbstractRspModel> R proxy(AbstractReqModel reqModel) throws ChannelServiceException {
        IChannelService channelService = channelServiceFactory.getChannelService(reqModel.getChannel(), reqModel.getTrans());
        if (channelService == null) {
            throw new ChannelServiceException(RspCodeEnum.PARAM_ERROR);
        }
        R rspModel = (R) channelService.invoke(reqModel);
        return rspModel;
    }
}
