package io.github.pleuvoir.channel.channels.alipay;

import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.request.PaymentDTO;
import io.github.pleuvoir.channel.model.response.PaymentResultDTO;
import io.github.pleuvoir.channel.plugin.IChannelService;

/**
 * 微信当面付扫码支付
 *
 * <p>使用场景：
 * 线下买家通过使用支付宝钱包扫一扫，扫描商家的二维码等方式完成支付。
 *
 * <p>和原生扫码的区别是：原生不需要输入金额，这种的需要用户输入支付金额
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class AlipayScanCode implements IChannelService<PaymentDTO, PaymentResultDTO> {

    @Override
    public PaymentResultDTO invoke(PaymentDTO request) throws ChannelServiceException {
        return null;
    }
}
