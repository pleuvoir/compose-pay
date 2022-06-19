package io.github.pleuvoir.channel.channels.wechat;

import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.request.PaymentDTO;
import io.github.pleuvoir.channel.model.response.PaymentResultDTO;
import io.github.pleuvoir.channel.channels.IChannelService;

/**
 * 微信APP支付
 *
 * <p>使用场景：
 * APP支付是指商户通过在移动端应用APP中集成开放SDK调起微信支付模块来完成支付。适用于在移动端APP中集成微信支付功能的场景。
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class WechatApp  {

    public PaymentResultDTO invoke(PaymentDTO request) throws ChannelServiceException {
        return null;
    }
}
