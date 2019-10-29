package io.github.pleuvoir.gateway.service.internal;

import io.github.pleuvoir.gateway.model.po.MerChannelPO;
import io.github.pleuvoir.gateway.model.po.MerchantPO;

/**
 * 路由服务
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface RouteService {

    /**
     * 找到适合商户的渠道
     *
     * @param mid         商户号
     * @param productCode 支付产品编码
     * @return
     */
    MerChannelPO find(String mid, String productCode);
}
