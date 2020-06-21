package io.github.pleuvoir.gateway.service;

import io.github.pleuvoir.gateway.model.po.MerPayPO;
import io.github.pleuvoir.gateway.model.po.MerchantPO;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface ITransactionService {

    MerPayPO createOrder(MerPayPO order, MerchantPO merchant);

    MerPayPO queryOrderbyES();
}
