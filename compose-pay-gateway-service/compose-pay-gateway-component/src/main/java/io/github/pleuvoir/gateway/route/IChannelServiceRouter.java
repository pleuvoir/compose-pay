package io.github.pleuvoir.gateway.route;

import io.github.pleuvoir.gateway.model.dto.ChannelRouterDTO;
import io.github.pleuvoir.gateway.model.dto.ChannelRouterResultDTO;

/**
 * 渠道服务路由
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface IChannelServiceRouter {

    /**
     * 确定具体的
     * <p>
     * 服务ID、渠道
     */
    ChannelRouterResultDTO route(ChannelRouterDTO routerDTO);
}
