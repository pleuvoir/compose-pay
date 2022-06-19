package io.github.pleuvoir.channel.channels;

import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.shared.AbstractReqModel;
import io.github.pleuvoir.channel.model.shared.AbstractRspModel;

/**
 * 抽象通道服务接口
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public interface IChannelService<T extends AbstractReqModel, R extends AbstractRspModel> {

    R invoke(T request) throws ChannelServiceException;
}
