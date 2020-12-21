package io.github.pleuvoir.gateway.route;

import io.github.pleuvoir.gateway.model.dto.ChannelRouterDTO;
import io.github.pleuvoir.gateway.model.dto.ChannelRouterResultDTO;
import org.springframework.stereotype.Service;

/**
 * 简单渠道服务路由
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Service
public class SimpleChannelServiceRouter implements IChannelServiceRouter {

    @Override
    public ChannelRouterResultDTO route(ChannelRouterDTO routerDTO) {
        return null;
    }
}
