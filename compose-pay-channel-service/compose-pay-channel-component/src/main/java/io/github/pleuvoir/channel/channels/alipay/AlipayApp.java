package io.github.pleuvoir.channel.channels.alipay;

import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.request.PaymentDTO;
import io.github.pleuvoir.channel.model.response.PaymentResultDTO;
import io.github.pleuvoir.channel.channels.IChannelService;

/**
 * 支付宝APP支付
 *
 * <p>使用场景：
 * 商家APP集成支付宝提供的支付能力，在线上轻松收款：用户在商家APP消费，自动跳转支付宝完成付款，付款后自动跳回。
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class AlipayApp {

    public PaymentResultDTO invoke(PaymentDTO request) throws ChannelServiceException {
        return null;
    }


}
