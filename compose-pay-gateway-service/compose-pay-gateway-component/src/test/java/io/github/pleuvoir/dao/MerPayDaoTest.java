package io.github.pleuvoir.dao;

import io.github.pleuvoir.BaseTest;
import io.github.pleuvoir.gateway.dao.pay.IMerPayDao;
import io.github.pleuvoir.gateway.model.po.MerPayPO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class MerPayDaoTest extends BaseTest {

    @Autowired
    private IMerPayDao dao;

    @Test
    public void createOrderTest() {

        MerPayPO merPayPO = new MerPayPO();
        merPayPO.setSerialNo(Long.valueOf(4));
        merPayPO.setTransUniqueId(System.currentTimeMillis());

        Integer ret = dao.insert(merPayPO);
        System.out.println(ret > 0);

    }
}
