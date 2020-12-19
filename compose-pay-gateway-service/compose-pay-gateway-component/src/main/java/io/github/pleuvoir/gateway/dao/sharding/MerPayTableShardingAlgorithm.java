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
package io.github.pleuvoir.gateway.dao.sharding;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * 复合分表规则
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
public class MerPayTableShardingAlgorithm implements ComplexKeysShardingAlgorithm<Long> {

    private static final String KEY_SERIAL_NO = "serial_no";
    private static final String KEY_TRANS_UNIQUE_ID = "trans_unique_id";

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Long> complexKeysShardingValue) {

        //当前实际表
        Object[] targetTables = availableTargetNames.toArray();

        Map<String, Collection<Long>> shardingValuesMap = complexKeysShardingValue.getColumnNameAndShardingValuesMap();

        Collection<Long> serialNoList = shardingValuesMap.get(KEY_SERIAL_NO);

        //如果平台流水号不为空
        if (CollectionUtils.isNotEmpty(serialNoList)) {
            Long serialNo = (Long) serialNoList.toArray()[0];
            String targetTable = String.valueOf(targetTables[(int) (serialNo % 4)]);
            log.info("serialNo={}，targetTable={}", serialNo, targetTable);
            return Collections.singletonList(targetTable);
        }

        //如果唯一流水号不为空
        Collection<Long> transUniqueIdList = shardingValuesMap.get(KEY_TRANS_UNIQUE_ID);
        if (CollectionUtils.isNotEmpty(transUniqueIdList)) {
            Long transUniqueId = (Long) transUniqueIdList.toArray()[0];
            String targetTable = String.valueOf(targetTables[(int) (transUniqueId % 4)]);
            log.info("serialNo={}，targetTable={}", transUniqueId, targetTable);
            return Collections.singletonList(targetTable);
        }

        return null;
    }

}
