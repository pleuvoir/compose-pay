package io.github.pleuvoir.channel.channels.wechat;

import io.github.pleuvoir.channel.common.AbstractReqModel;
import io.github.pleuvoir.channel.common.AbstractRspModel;
import io.github.pleuvoir.channel.core.IChannelService;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class WechatPayQuery implements IChannelService
{
    @Override
    public <R extends AbstractReqModel, P extends AbstractRspModel> P invoke(R model) {
        return null;
    }
}
