package io.github.pleuvoir.gateway.service.internal;

import io.github.pleuvoir.gateway.model.po.MerSignFeePO;
import io.github.pleuvoir.gateway.model.po.MerchantPO;

import java.util.List;

/**
 * 基础服务
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface BaseService {

    /**
     * 获取商户信息
     *
     * @param mid 商户号
     */
    MerchantPO getMerchant(String mid);

    /**
     * 获取商户签约的支付产品
     *
     * @param mid            商户号
     * @param payProductCode 支付产品编号
     */
    MerSignFeePO getMerSignsByMidAndPayProductCode(String mid, String payProductCode);

    /**
     * 获取商户签约的支付产品
     *
     * @param mid     商户号
     * @param payType 支付种类编号
     * @param payWay  支付方式编号
     */
    MerSignFeePO getMerSignsByMidAndPayWayAndPayType(String mid, String payType, String payWay);
}
