package io.github.pleuvoir.gateway.service.internal;

import io.github.pleuvoir.gateway.model.po.PayProductPO;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface PayProductService {

    PayProductPO getByCode(String code);
}
