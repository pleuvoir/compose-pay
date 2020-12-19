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
package io.github.pleuvoir.gateway;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.sql.DataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableCaching
@AutoConfigureAfter({RedisAutoConfiguration.class})
@EnableDubbo(scanBasePackages = {"io.github.pleuvoir.gateway"})
@MapperScan("io.github.pleuvoir.gateway.dao")
public class PayGatewayConfiguration {

    /**
     * mybatis-plus
     */
    @Bean("sqlSessionFactory")
    public MybatisSqlSessionFactoryBean getMybatisSqlSessionFactoryBean(DataSource dataSource)
            throws IOException {
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
    public RedisTemplate<String, Object> getRedisTemplate(
            LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
        template.setValueSerializer(new GenericFastJsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
        template.setHashValueSerializer(new GenericFastJsonRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean("stringRedisTemplate")
    public StringRedisTemplate getStringRedisTemplate(
            LettuceConnectionFactory redisConnectionFactory) {
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
