package io.github.pleuvoir.gateway.service.internal.impl;

import io.github.pleuvoir.gateway.dao.mer.MerChantDao;
import io.github.pleuvoir.gateway.model.po.MerchantPO;
import io.github.pleuvoir.gateway.service.internal.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerChantDao merChantDao;

    @Override
    public MerchantPO getByMid(String mid) {
        MerchantPO query = new MerchantPO();
        query.setMid(mid);
        MerchantPO merchantPO = merChantDao.selectOne(query);
        return merchantPO;
    }

}
