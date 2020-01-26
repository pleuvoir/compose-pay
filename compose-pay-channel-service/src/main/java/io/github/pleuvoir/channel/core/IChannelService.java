package io.github.pleuvoir.channel.core;

import io.github.pleuvoir.channel.common.AbstractReqModel;
import io.github.pleuvoir.channel.common.AbstractRspModel;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface IChannelService {

    <R extends AbstractReqModel, P extends AbstractRspModel> P invoke(R model);
}
