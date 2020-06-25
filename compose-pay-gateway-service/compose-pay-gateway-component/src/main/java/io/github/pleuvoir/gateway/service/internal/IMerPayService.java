package io.github.pleuvoir.gateway.service.internal;

import io.github.pleuvoir.gateway.model.po.MerPayPO;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface IMerPayService {

    /**
     * 根据平台流水号查询支付订单
     *
     * @param serialNo 平台流水号
     */
    MerPayPO getBySerialNo(String serialNo);

    /**
     * 保存支付订单
     */
    Integer save(MerPayPO merPayPO);

}
