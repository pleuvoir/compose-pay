package io.github.pleuvoir.channel.plugin;

import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import io.github.pleuvoir.channel.common.ChannelEnum;
import io.github.pleuvoir.channel.common.ServiceIdEnum;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的通道道服务工厂<br>
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
public class DefaultChannelServiceFactory implements IChannelServiceFactory, InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Setter
    private ChannelServicePlugin channelServicePlugin;


    private Table<ChannelEnum, ServiceIdEnum, IChannelService> channelTransServiceTable = Tables.newCustomTable(new ConcurrentHashMap<>(), ConcurrentHashMap::new);

    /**
     * 获取通道交易处理类
     *
     * @param channel   通道枚举
     * @param serviceId 服务类别
     */
    @Override
    public IChannelService getChannelService(ChannelEnum channel, ServiceIdEnum serviceId) {
        IChannelService channelService = channelTransServiceTable.get(channel, serviceId);
        if (channelService == null) {
            log.error("获取通道服务失败，channel={}，scene={}", channel, serviceId);
        }
        return channelService;
    }

    @Override
    public void afterPropertiesSet() {
        BeanDefinitionRegistry beanRegistry = (BeanDefinitionRegistry) applicationContext;
        channelServicePlugin.getChannels().forEach((ChannelEnum channel) -> {
            channelServicePlugin.getServiceMap(channel).forEach((ServiceIdEnum serviceId, Class<?> service) -> {
                String serviceName = String.format("%s_%s", channel.name(), service.getSimpleName());
                BeanDefinitionBuilder beanBuilder = BeanDefinitionBuilder.genericBeanDefinition(service);
                beanRegistry.registerBeanDefinition(serviceName, beanBuilder.getBeanDefinition());
                channelTransServiceTable.put(channel, serviceId, (IChannelService) applicationContext.getBean(serviceName));
            });
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
