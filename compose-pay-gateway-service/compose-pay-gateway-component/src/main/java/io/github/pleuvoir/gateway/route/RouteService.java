package io.github.pleuvoir.gateway.route;

import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.po.MerChannelPO;

/**
 * 路由服务
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface RouteService {

    /**
     * 找到适合商户的渠道
     *
     * @param mid     商户号
     * @param payType 支付种类编码
     * @param payWay  支付方式编码
     */
    MerChannelPO find(String mid, String payType, String payWay) throws BusinessException;
}
