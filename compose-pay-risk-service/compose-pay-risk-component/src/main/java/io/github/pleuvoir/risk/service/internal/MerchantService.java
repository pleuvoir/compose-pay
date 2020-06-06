package io.github.pleuvoir.gateway.service.internal;

import io.github.pleuvoir.gateway.model.po.MerchantPO;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface MerchantService {

    /**
     * 根据mid获取商户信息，不存在时返回null
     * @param mid 商户号
     */
    MerchantPO getByMid(String mid);
}
