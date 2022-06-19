package io.github.pleuvoir.channel.channels.alipay;

import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.request.PaymentDTO;
import io.github.pleuvoir.channel.model.response.PaymentResultDTO;
import io.github.pleuvoir.channel.channels.IChannelService;

/**
 * 支付宝电脑网站支付
 *
 * <p>使用场景：
 * PC网站轻松收款，资金马上到账：用户在商家PC网站消费，自动跳转支付宝PC网站收银台完成付款。 交易资金直接打入商家支付宝账户，实时到账。
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class AlipayPcWeb {

    public PaymentResultDTO invoke(PaymentDTO request) throws ChannelServiceException {
        return null;
    }


}
