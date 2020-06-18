package io.github.pleuvoir.channel.channels.alipay;

import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.request.PaymentDTO;
import io.github.pleuvoir.channel.model.response.PaymentResultDTO;
import io.github.pleuvoir.channel.plugin.IChannelService;

/**
 * 支付宝手机网站支付
 *
 * <p>使用场景：
 * 无需开发APP，手机网站同样能轻松收款：用户在商家手机网站消费，通过浏览器自动跳转支付宝APP或支付宝网页完成付款。 轻松实现和APP支付相同的支付体验。
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class AlipayMobileH5 implements IChannelService<PaymentDTO, PaymentResultDTO> {

    @Override
    public PaymentResultDTO invoke(PaymentDTO request) throws ChannelServiceException {
        return null;
    }
}
