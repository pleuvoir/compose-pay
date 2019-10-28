package io.github.pleuvoir.gateway.service.internal;

import io.github.pleuvoir.gateway.model.po.MerSignFeePO;

import java.util.List;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface MerSignFeeService {

    /**
     * 查询商户签约的支付产品
     * @param mid 商户号
     * @param payProductCode 支付产品
     */
    List<MerSignFeePO> getByMidAndPayProductCode(String mid, String payProductCode);
}
