package io.github.pleuvoir.gateway.service.internal;

import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.po.MerChannelPO;

import java.util.List;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface MerChannelService {

    /**
     * 获取可用的商户通道关系
     *
     * @param mid     商户号
     * @param payType 支付种类
     * @param payWay  支付方式
     */
    List<MerChannelPO> getUsableMerCnlList(String mid, String payType, String payWay);

    /**
     * 最高优先级的商户通道关系
     *
     * @param mid     商户号
     * @param payType 支付种类
     * @param payWay  支付方式
     */
    MerChannelPO maxPriority(String mid, String payType, String payWay);
}
