package io.github.pleuvoir.channel.agent;

import io.github.pleuvoir.channel.model.request.NotifyParamDTO;
import io.github.pleuvoir.channel.model.response.NotifyParamResultDTO;

/**
 * 通道通知服务
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public interface INotifyChannelServiceAgent {

    /**
     * 处理通道支付结果通知
     */
    NotifyParamResultDTO payNotifyReceive(NotifyParamDTO notifyParamDTO);

    /**
     * 接收退款通知
     */
    NotifyParamResultDTO refundReceive(NotifyParamDTO notifyParamDTO);
}
