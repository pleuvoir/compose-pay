/*
 * Copyright © 2020 pleuvoir (pleuvior@foxmail.com)
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

import com.alibaba.druid.pool.DruidDataSource;
import io.github.pleuvoir.BaseTest;
import io.github.pleuvoir.gateway.dao.pay.IMerPayDao;
import io.github.pleuvoir.gateway.model.po.MerPayPO;
import java.util.Map;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.junit.Test;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class MerPayDaoTest extends BaseTest {

    @Resource
    private IMerPayDao dao;

    @Resource
    private ShardingDataSource dataSource;

    @Test
    public void createOrderTest() throws InterruptedException {

        Map<String, DataSource> dataSourceMap = dataSource.getDataSourceMap();

        DruidDataSource m1 = (DruidDataSource) dataSourceMap.get("m1");
        System.out.println(StringUtils.repeat("*", 20));

        for (int i = 0; i < 4; i++) {
            MerPayPO merPayPO = new MerPayPO();
            merPayPO.setSerialNo((long) i);

            Integer ret = dao.insert(merPayPO);
            System.out.println(ret > 0);
        }

        Thread.currentThread().join();

    }
}
