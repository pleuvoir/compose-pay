package io.github.pleuvoir.channel;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import io.github.pleuvoir.channel.common.util.RetryRejectedExecutionHandler;
import io.github.pleuvoir.channel.factory.DefaultChannelServiceFactory;
import io.github.pleuvoir.channel.factory.IChannelServiceFactory;
import io.github.pleuvoir.channel.plugins.ChannelServicePlugin;
import io.github.pleuvoir.channel.plugins.DefaultChannelServicePlugin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 通道服务配置
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Configuration
@EnableDubbo(scanBasePackages = {"io.github.pleuvoir.channel"})
@ComponentScan("io.github.pleuvoir.channel.*")
public class PayChannelConfiguration {


    /**
     * 通道服务插件配置
     */
    @Bean(name = "channelServicePlugin", initMethod = "load")
    public ChannelServicePlugin channelServicePlugin() {
        ChannelServicePlugin servicePlugin = new DefaultChannelServicePlugin();
        servicePlugin.setLocation("classpath:config/plugins/channel/*.xml");
        return servicePlugin;
    }

    /**
     * 通道服务工厂
     */
    @Bean
    public IChannelServiceFactory channelServiceFactory(ChannelServicePlugin servicePlugin) {
        DefaultChannelServiceFactory channelServiceFactory = new DefaultChannelServiceFactory();
        channelServiceFactory.setChannelServicePlugin(servicePlugin);
        return channelServiceFactory;
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
