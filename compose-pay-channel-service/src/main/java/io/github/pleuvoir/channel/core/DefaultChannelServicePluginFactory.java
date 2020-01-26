package io.github.pleuvoir.channel.core;

import com.google.common.base.Supplier;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import io.github.pleuvoir.channel.common.ChannelEnum;
import io.github.pleuvoir.channel.common.TransEnum;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
public class DefaultChannelServicePluginFactory implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Setter
    private ChannelServicePlugin channelServicePlugin;


    private Table<ChannelEnum, TransEnum, IChannelService> channelTransServiceTable = Tables.newCustomTable(new ConcurrentHashMap<ChannelEnum, Map<TransEnum, IChannelService>>(), new Supplier<Map<TransEnum, IChannelService>>() {
        @Override
        public Map<TransEnum, IChannelService> get() {
            return new ConcurrentHashMap<>();
        }
    });

    /**
     * 获取通道交易处理类
     *
     * @param channel 渠道枚举
     * @param trans   交易枚举
     */
    public IChannelService getChannelService(ChannelEnum channel, TransEnum trans) {
        IChannelService channelService = channelTransServiceTable.get(channel, trans);
        if (channelService == null) {
            log.warn("获取服务失败，channel={}，trans={}", channel, trans);
            //  throw new BusinessException(ResultCodeEnum.COMMON_CODE_FAIL, "暂无可用的服务");
            // TODO
        }
        return channelService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        BeanDefinitionRegistry beanRegistry = (BeanDefinitionRegistry) applicationContext;
        channelServicePlugin.getChannels().forEach((ChannelEnum channel) -> {
            channelServicePlugin.getTransMap(channel).forEach((TransEnum trans, Class<?> service) -> {
                String serviceName = channel.name() + "_" + service.getSimpleName();
                BeanDefinitionBuilder beanBuilder = BeanDefinitionBuilder.genericBeanDefinition(service);
                beanRegistry.registerBeanDefinition(serviceName, beanBuilder.getBeanDefinition());
                channelTransServiceTable.put(channel, trans, (IChannelService) applicationContext.getBean(serviceName));
            });
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
