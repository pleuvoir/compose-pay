package io.github.pleuvoir.gateway.dao.sharding;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.Collection;
import java.util.Map;

/**
 * 复合分表规则
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
public class MultipleKeysModuleTableShardingAlgorithm implements ComplexKeysShardingAlgorithm<Long> {

    private final String KEY_PAY_ID = "pay_id";
    private final String KEY_BUSINESS_UNIQUE_ID = "busi_unique_id";

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Long> complexKeysShardingValue) {

        String logicTableName = complexKeysShardingValue.getLogicTableName();
        Map<String, Collection<Long>> shardingValuesMap = complexKeysShardingValue.getColumnNameAndShardingValuesMap();

        Collection<Long> ps = shardingValuesMap.get(KEY_PAY_ID);
        Collection<Long> bs = shardingValuesMap.get(KEY_BUSINESS_UNIQUE_ID);

        return null;
    }

}
