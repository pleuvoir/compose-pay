package io.github.pleuvoir.channel;

import io.github.pleuvoir.channel.common.util.ApplicationContextHelper;
import io.github.pleuvoir.channel.common.util.RetryRejectedExecutionHandler;
import io.github.pleuvoir.channel.factory.AnnotationChannelServiceFactory;
import io.github.pleuvoir.channel.factory.PluginChannelServiceFactory;
import io.github.pleuvoir.channel.factory.IChannelServiceFactory;
import io.github.pleuvoir.channel.plugins.ChannelServicePlugin;
import io.github.pleuvoir.channel.plugins.DefaultChannelServicePlugin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * 通道服务配置
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Configuration
//@EnableDubbo(scanBasePackages = {"io.github.pleuvoir.channel"})
@ComponentScan("io.github.pleuvoir.channel.*")
public class PayChannelConfiguration {

//
//    /**
//     * 通道服务插件配置
//     */
//    @Bean(name = "channelServicePlugin", initMethod = "load")
//    public ChannelServicePlugin channelServicePlugin() {
//        ChannelServicePlugin servicePlugin = new DefaultChannelServicePlugin();
//        servicePlugin.setLocation("classpath:plugins/channel/*.xml");
//        return servicePlugin;
//    }
//
//    /**
//     * 插件通道服务工厂
//     */
//    @Bean(name = PluginChannelServiceFactory.BEAN_NAME)
//    public PluginChannelServiceFactory pluginChannelServiceFactory(ChannelServicePlugin servicePlugin) {
//        PluginChannelServiceFactory channelServiceFactory = new PluginChannelServiceFactory();
//        channelServiceFactory.setChannelServicePlugin(servicePlugin);
//        return channelServiceFactory;
//    }


    /**
     * 注解通道服务工厂
     */
    @Bean(name = AnnotationChannelServiceFactory.BEAN_NAME, initMethod = "initialization")
    public AnnotationChannelServiceFactory annotationChannelServiceFactory() {
        return new AnnotationChannelServiceFactory();
    }


    @Bean
    public ApplicationContextHelper applicationContextHelper(){
        return new ApplicationContextHelper();
    }

    /**
     * 最大努力重试线程池
     */
    @ConfigurationProperties(prefix = "spring.executor")
    @Bean(name = "threadPoolTaskExecutor", initMethod = "initialize")
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
        poolTaskExecutor.setRejectedExecutionHandler(new RetryRejectedExecutionHandler());
        return poolTaskExecutor;
    }

}
