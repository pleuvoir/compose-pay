package io.github.pleuvoir.channel.channels.wechat;

import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.request.PaymentDTO;
import io.github.pleuvoir.channel.model.response.PaymentResultDTO;
import io.github.pleuvoir.channel.channels.IChannelService;

/**
 * 微信JSAPI公众号支付
 *
 * <p>使用场景：
 * 公众号场景：用户在微信公众账号内进入商家公众号，打开某个主页面，完成支付
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class WechatGz  {

    public PaymentResultDTO invoke(PaymentDTO request)  {
        return null;
    }
}
