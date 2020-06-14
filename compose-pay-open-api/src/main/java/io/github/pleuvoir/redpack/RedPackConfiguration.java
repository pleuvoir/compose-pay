package io.github.pleuvoir.redpack;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import io.github.pleuvoir.redpack.common.AppConfig;
import io.github.pleuvoir.redpack.common.utils.Locker;
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
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Configuration
@EnableTransactionManagement
@EnableCaching
@AutoConfigureAfter({RedisAutoConfiguration.class})
@MapperScan("io.github.pleuvoir.redpack.dao")
public class RedPackConfiguration implements WebMvcConfigurer {


    /**
     * mybatis-plus
     */
    @Bean("sqlSessionFactory")
    public MybatisSqlSessionFactoryBean getMybatisSqlSessionFactoryBean(DataSource dataSource) throws IOException {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        factoryBean.setConfigLocation(new ClassPathResource("mapping-config.xml"));
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/*Mapper.xml"));

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
    public <T> RedisTemplate<String, T> getRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer(Charset.forName("UTF-8")));
        template.setValueSerializer(new GenericFastJsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer(Charset.forName("UTF-8")));
        template.setHashValueSerializer(new GenericFastJsonRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        template.afterPropertiesSet();
        return template;
    }

    @Bean("stringRedisTemplate")
    public StringRedisTemplate getStringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    
	@Bean(name = "redisLocker")
	public Locker redisLocker(RedisConnectionFactory connectionFactory) {
		return new Locker(connectionFactory, "REDIS_LOCK");
	}
    
    @ConfigurationProperties(prefix = "spring.executor")
    @Bean(name = "threadPoolTaskExecutor", initMethod = "initialize")
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }


    @Bean(name = "appConfig")
    @ConfigurationProperties(prefix = "app.config")
    public AppConfig appConfig() {
        return new AppConfig();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter jsonConverter = new FastJsonHttpMessageConverter();
        List<MediaType> jsonMediaTypes = new ArrayList<>();
        jsonMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        jsonConverter.setSupportedMediaTypes(jsonMediaTypes);
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        List<MediaType> stringMediaTypes = new ArrayList<>();
        stringMediaTypes.add(MediaType.TEXT_PLAIN);
        stringConverter.setSupportedMediaTypes(stringMediaTypes);
        converters.add(stringConverter);
        converters.add(jsonConverter);
    }

}
