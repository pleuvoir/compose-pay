package io.github.pleuvoir.gateway.dao.sharding;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;


@Slf4j
public class MerPayPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {

        Long payId = preciseShardingValue.getValue();


        String db = "ds" + (payId % 8 + 1);
        log.info("分片节点为：" + db);
        //  ds0,ds1

        //
        //
        //  for (String tableName : availableTargetNames) {
        //            if (tableName.endsWith((shardingValue.getValue() % 3) + 1 + "")) {
        //                return tableName;
        //            }
        //        }
        return db;
    }

}
