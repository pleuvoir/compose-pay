package io.github.pleuvoir.gateway;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;

@Configuration
@EnableTransactionManagement
@EnableCaching
@AutoConfigureAfter({RedisAutoConfiguration.class})
@EnableDubbo(scanBasePackages = {"io.github.pleuvoir.risk"})
@MapperScan("io.github.pleuvoir.risk.dao")
public class PayRiskConfiguration {

    /**
     * mybatis-plus
     */
    @Bean("sqlSessionFactory")
    public MybatisSqlSessionFactoryBean getMybatisSqlSessionFactoryBean(DataSource dataSource) throws IOException {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        factoryBean.setConfigLocation(new ClassPathResource("mapping-config.xml"));
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/**/*Mapper.xml"));

        GlobalConfiguration globalConfig = new GlobalConfiguration();
        globalConfig.setIdType(IdType.ID_WORKER.getKey());
        globalConfig.setDbType(DBType.MYSQL.getDb());
        globalConfig.setDbColumnUnderline(true);
        factoryBean.setGlobalConfig(globalConfig);

        factoryBean.setPlugins(new Interceptor[]{
                new PaginationInterceptor(),
                new OptimisticLockerInterceptor()
        });
        return factoryBean;
    }


    @Bean("transactionManager")
    public DataSourceTransactionManager getDataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * redis
     */
    @Bean("redisTemplate")
    public RedisTemplate<String, Object> getRedisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer(Charset.forName("UTF-8")));
        template.setValueSerializer(new GenericFastJsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer(Charset.forName("UTF-8")));
        template.setHashValueSerializer(new GenericFastJsonRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean("stringRedisTemplate")
    public StringRedisTemplate getStringRedisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @ConfigurationProperties(prefix = "spring.executor")
    @Bean(name = "threadPoolTaskExecutor", initMethod = "initialize")
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }
}
