package io.github.pleuvoir.gateway.service.internal.impl;

import io.github.pleuvoir.gateway.dao.pay.PayProductDao;
import io.github.pleuvoir.gateway.model.po.PayProductPO;
import io.github.pleuvoir.gateway.service.internal.PayProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Service
public class PayProductServiceImpl implements PayProductService {

    @Autowired
    private PayProductDao productDao;

    @Override
    public PayProductPO getByCode(String code) {
        PayProductPO query = new PayProductPO();
        query.setPayProductCode(code);
        PayProductPO payProductPO = productDao.selectOne(query);
        return payProductPO;
    }
}
