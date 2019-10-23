package io.github.pleuvoir.gateway.service.internal;

import io.github.pleuvoir.gateway.model.po.MerchantPO;

/**
 * 基础服务
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface BaseService {

    /**
     * 获取商户信息
     * @param mid 商户号
     */
    MerchantPO getMerchant(String mid);
}
