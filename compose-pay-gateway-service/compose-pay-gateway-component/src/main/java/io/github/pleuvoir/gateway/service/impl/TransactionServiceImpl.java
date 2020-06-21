package io.github.pleuvoir.gateway.service.impl;

import io.github.pleuvoir.gateway.model.po.MerPayPO;
import io.github.pleuvoir.gateway.model.po.MerchantPO;
import io.github.pleuvoir.gateway.service.ITransactionService;
import io.github.pleuvoir.gateway.service.internal.IMerPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
@Service
public class TransactionServiceImpl implements ITransactionService {

    @Autowired
    private IMerPayService merPayService;


    @Override
    public MerPayPO createOrder(MerPayPO order, MerchantPO merchant) {

        merPayService.save(order);
        return null;
    }

    @Override
    public MerPayPO queryOrderbyES() {
        return null;
    }
}
