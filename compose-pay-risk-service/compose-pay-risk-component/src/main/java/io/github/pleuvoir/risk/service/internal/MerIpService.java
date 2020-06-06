package io.github.pleuvoir.gateway.service.internal;

import io.github.pleuvoir.gateway.exception.BusinessException;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface MerIpService {

    /**
     * IP限制，判断用户是否绑定IP
     *
     * @param mid 商户号
     * @param ip
     */
    void ipLimit(String mid, String ip) throws BusinessException;
}
