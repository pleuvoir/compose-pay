/*
 * Copyright © 2020 pleuvoir (pleuvoir@foxmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
