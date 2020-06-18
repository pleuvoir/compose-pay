package io.github.pleuvoir.channel.channels.alipay;

import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.request.PaymentDTO;
import io.github.pleuvoir.channel.model.response.PaymentResultDTO;
import io.github.pleuvoir.channel.plugin.IChannelService;

/**
 * 支付宝APP支付
 *
 * <p>使用场景：
 * 商家APP集成支付宝提供的支付能力，在线上轻松收款：用户在商家APP消费，自动跳转支付宝完成付款，付款后自动跳回。
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class AlipayApp implements IChannelService<PaymentDTO, PaymentResultDTO> {

    @Override
    public PaymentResultDTO invoke(PaymentDTO request) throws ChannelServiceException {
        return null;
    }

    /**
     * 微信JSAPI扫码支付
     *
     * <p>使用场景：
     * 线下场所：调用接口生成二维码，用户扫描二维码后在微信浏览器中打开页面后完成支付
     * PC网站场景：在网站中展示二维码，用户扫描二维码后在微信浏览器中打开页面后完成支付
     *
     * <p>和原生扫码的区别是：原生不需要输入金额，这种的需要用户输入支付金额
     *
     * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
     */
    public static class AlipayScanCode implements IChannelService<PaymentDTO, PaymentResultDTO> {

        @Override
        public PaymentResultDTO invoke(PaymentDTO request) throws ChannelServiceException {
            return null;
        }
    }
}
