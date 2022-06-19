package io.github.pleuvoir.channel.channels.mock;

import io.github.pleuvoir.channel.channels.IChannelService;
import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.request.PaymentDTO;
import io.github.pleuvoir.channel.model.response.PaymentResultDTO;

/**
 * 微信手机网站支付
 *
 * <p>使用场景：
 * 主要用于触屏版的手机浏览器请求微信支付的场景。可以方便的从外部浏览器唤起微信支付。
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class MockPay implements IChannelService<PaymentDTO, PaymentResultDTO> {

    @Override
    public PaymentResultDTO invoke(PaymentDTO request) throws ChannelServiceException {
        return null;
    }
}
