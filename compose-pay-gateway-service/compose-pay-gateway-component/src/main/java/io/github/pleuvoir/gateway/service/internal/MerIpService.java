package io.github.pleuvoir.gateway.service.internal;

import io.github.pleuvoir.gateway.exception.BusinessException;

/**
 * 商户IP服务
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface MerIpService {

    /**
     * IP限制，判断用户是否绑定IP
     */
    void ipLimit(String mid, String ip) throws BusinessException;
}
