package io.github.pleuvoir.channel.core;

import io.github.pleuvoir.channel.common.AbstractReqModel;
import io.github.pleuvoir.channel.common.AbstractRspModel;

/**
 * 抽象渠道服务接口，每个上游API接口对应一个实现类
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface IChannelService {

    <R extends AbstractReqModel, P extends AbstractRspModel> P invoke(R model);
}
