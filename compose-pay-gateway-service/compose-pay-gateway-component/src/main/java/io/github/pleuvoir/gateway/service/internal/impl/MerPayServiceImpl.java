package io.github.pleuvoir.gateway.service.internal.impl;

import io.github.pleuvoir.gateway.dao.pay.IMerPayDao;
import io.github.pleuvoir.gateway.model.po.MerPayPO;
import io.github.pleuvoir.gateway.service.internal.IMerPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Service
public class MerPayServiceImpl implements IMerPayService {


    @Autowired
    private IMerPayDao merPayDao;


    @Override
    public MerPayPO getBySerialNo(String serialNo) {
        return null;
    }

    @Override
    public void save(MerPayPO merPayPO) {
        Integer ret = merPayDao.insert(merPayPO);
    }
}
