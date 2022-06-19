package io.github.pleuvoir.channel.channels.wechat;

import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.request.PaymentDTO;
import io.github.pleuvoir.channel.model.response.PaymentResultDTO;
import io.github.pleuvoir.channel.channels.IChannelService;

/**
 * 微信手机网站支付
 *
 * <p>使用场景：
 * 主要用于触屏版的手机浏览器请求微信支付的场景。可以方便的从外部浏览器唤起微信支付。
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class WechatMobileH5  {

    public PaymentResultDTO invoke(PaymentDTO request) throws ChannelServiceException {
        return null;
    }
}
