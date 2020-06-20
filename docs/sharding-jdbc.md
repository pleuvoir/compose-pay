



### pom 



```xml
<sharding-sphere.version>4.0.0-RC1</sharding-sphere.version>
<!--shardingsphere start-->
     	<dependency>
            <groupId>org.apache.shardingsphere</groupId>
            <artifactId>sharding-jdbc-spring-boot-starter</artifactId>
            <version>${sharding-sphere.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.shardingsphere</groupId>
            <artifactId>sharding-core-common</artifactId>
            <version>${sharding-sphere.version}</version>
        </dependency>
<!--shardingsphere end-->
```





### application-分表.properties



```properties
# 数据源配置
spring.shardingsphere.datasource.name=ds
spring.shardingsphere.datasource.ds.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.ds.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.ds.url=jdbc:mysql://localhost:3306/order?characterEncoding=utf-8
spring.shardingsphere.datasource.ds.username=root
spring.shardingsphere.datasource.ds.password=123456

# 分表后的表名 t_order1 t_order2 t_order3
spring.shardingsphere.sharding.tables.t_order.actual-data-nodes=ds.t_order$->{1..3}

# 分片字段 order_id
spring.shardingsphere.sharding.tables.t_order.table-strategy.inline.sharding-column=order_id

# 指定字段分片规则

#spring.shardingsphere.sharding.tables.t_order.table-strategy.standard.sharding-column=order_id

# 支持简单的表达式
spring.shardingsphere.sharding.tables.t_order.table-strategy.inline.algorithm-expression=t_order$->{order_id % 3 + 1}

#spring.shardingsphere.sharding.tables.t_order.table-strategy.standard.precise-algorithm-class-name=MyPreciseShardingAlgorithm


# 默认内置的分布式id生成策略 （给某个列指定id生成规则，插入的时候不需要写order_id字段会自动赋值）
spring.shardingsphere.sharding.tables.t_order.key-generator.column=order_id
spring.shardingsphere.sharding.tables.t_order.key-generator.type=SNOWFLAKE

# 未配置分片规则的表将走默认数据源
sharding.jdbc.config.sharding.default-data-source-name=ds

spring.shardingsphere.props.sql.show=true
```



### application-读写分离.properties



```properties
spring.shardingsphere.datasource.names=master,slave0

spring.shardingsphere.datasource.master.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.master.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.master.url=jdbc:mysql://127.0.0.1:3400/order
spring.shardingsphere.datasource.master.username=root
spring.shardingsphere.datasource.master.password=123456

spring.shardingsphere.datasource.slave0.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.slave0.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.slave0.url=jdbc:mysql://127.0.0.1:3400/order
spring.shardingsphere.datasource.slave0.username=root
spring.shardingsphere.datasource.slave0.password=123456

spring.shardingsphere.masterslave.name=ms
spring.shardingsphere.masterslave.master-data-source-name=master
spring.shardingsphere.masterslave.slave-data-source-names=slave0

spring.shardingsphere.props.sql.show=true
```



### application-分库.properties



```properties
spring.shardingsphere.datasource.names=ds0,ds1

spring.shardingsphere.datasource.ds0.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.ds0.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.ds0.url=jdbc:mysql://127.0.0.1:3400/order?characterEncoding=utf-8
spring.shardingsphere.datasource.ds0.username=root
spring.shardingsphere.datasource.ds0.password=123456
spring.shardingsphere.datasource.ds1.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.ds1.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.ds1.url=jdbc:mysql://127.0.0.1:3400/order?characterEncoding=utf-8
spring.shardingsphere.datasource.ds1.username=root
spring.shardingsphere.datasource.ds1.password=123456

# 实际的数据节点，分库的数据源，对哪个表进行分库
spring.shardingsphere.sharding.tables.t_order.actual-data-nodes=ds$->{0..1}.t_order
#spring.shardingsphere.sharding.default-database-strategy.inline.sharding-column=orderId
#spring.shardingsphere.sharding.default-database-strategy.inline.algorithm-expression=ds$->{orderId % 2}

# 分片字段（这里是用时间来分片）
spring.shardingsphere.sharding.default-database-strategy.standard.sharding-column=createTime

# 自定义的分片规则（根据月份来分片，需要自己实现）
spring.shardingsphere.sharding.default-database-strategy.standard.precise-algorithm-class-name=cn.pleuvoir.algorithm.MonthPreciseShardingAlgorithm
spring.shardingsphere.sharding.tables.t_order.key-generator.column=orderId
spring.shardingsphere.sharding.tables.t_order.key-generator.type=SNOWFLAKE

spring.shardingsphere.props.sql.show=true

# 省略数据源、数据分片、读写分离和数据加密配置

spring.main.allow-bean-definition-overriding=true
```



以下是自定义的分片规则（根据月份来分片）

```java
public class MonthPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Date> preciseShardingValue) {
        String mon = String.format("%tm", preciseShardingValue.getValue());
        String db = "ds" + (Integer.valueOf(mon) % 2);
        log.info("分片节点为：" + db);
        //  ds0,ds1
        return db;
    }
}
```

