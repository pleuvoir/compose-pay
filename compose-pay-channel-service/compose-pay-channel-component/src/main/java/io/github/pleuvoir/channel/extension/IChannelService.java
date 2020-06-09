package io.github.pleuvoir.channel.extension;

import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.shared.AbstractReqModel;
import io.github.pleuvoir.channel.model.shared.AbstractRspModel;

/**
 * 抽象通道服务接口，每个上游API接口对应一个实现类
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface IChannelService<T extends AbstractReqModel, R extends AbstractRspModel> {

    R invoke(T request) throws ChannelServiceException;
}
