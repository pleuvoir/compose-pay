package io.github.pleuvoir.channel.channels.wechat;

import io.github.pleuvoir.channel.channels.IChannelService;
import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.request.PaymentDTO;
import io.github.pleuvoir.channel.model.response.PaymentResultDTO;

/**
 * 微信JSAPI扫码支付
 *
 * <p>使用场景：
 * 线下场所：调用接口生成二维码，用户扫描二维码后在微信浏览器中打开页面后完成支付
 * PC网站场景：在网站中展示二维码，用户扫描二维码后在微信浏览器中打开页面后完成支付
 *
 * <p>和原生扫码的区别是：原生不需要输入金额，这种的需要用户输入支付金额
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class WechatScanCode  {

    public PaymentResultDTO invoke(PaymentDTO request) throws ChannelServiceException {
        return null;
    }
}
