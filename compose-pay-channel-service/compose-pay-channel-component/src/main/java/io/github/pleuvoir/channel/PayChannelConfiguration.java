package io.github.pleuvoir.channel;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import io.github.pleuvoir.channel.plugin.ChannelServicePlugin;
import io.github.pleuvoir.channel.plugin.DefaultChannelServiceFactory;
import io.github.pleuvoir.channel.plugin.DefaultChannelServicePlugin;
import io.github.pleuvoir.channel.plugin.IChannelServiceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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

}
