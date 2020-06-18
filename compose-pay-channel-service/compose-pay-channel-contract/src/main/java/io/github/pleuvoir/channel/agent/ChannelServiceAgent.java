package io.github.pleuvoir.channel.agent;

import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.request.*;
import io.github.pleuvoir.channel.model.response.*;

/**
 * 通道服务
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface ChannelServiceAgent {

    /**
     * 下单
     */
    PaymentResultDTO payOrder(PaymentDTO paymentDTO) throws ChannelServiceException;

    /**
     * 订单支付结果查询
     */
    PayQueryResultDTO payQuery(PayQueryDTO payQueryDTO) throws ChannelServiceException;

    /**
     * 关闭订单
     */
    PaymentCloseResultDTO closeOrder(PaymentCloseDTO paymentCloseDTO) throws ChannelServiceException;

    /**
     * 退款申请
     */
    RefundResultDTO refund(RefundDTO refundDTO) throws ChannelServiceException;

    /**
     * 退款结果查询
     */
    RefundQueryResultDTO refundQuery(RefundQueryDTO refundQueryDTO) throws ChannelServiceException;

    /**
     * 处理通道支付结果通知
     */
    NotifyParamResultDTO payNotifyReceive(NotifyParamDTO notifyParamDTO);
}
