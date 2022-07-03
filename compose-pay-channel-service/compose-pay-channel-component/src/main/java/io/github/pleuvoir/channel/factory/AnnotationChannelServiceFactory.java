package io.github.pleuvoir.channel.factory;

import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import io.github.pleuvoir.channel.channels.IChannelService;
import io.github.pleuvoir.channel.common.annotation.ChannelService;
import io.github.pleuvoir.pay.common.enums.ChannelEnum;
import io.github.pleuvoir.pay.common.enums.ServiceTypeEnum;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

/**
 * 注解通道服务工厂
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Slf4j
public class AnnotationChannelServiceFactory implements IChannelServiceFactory {

    public static final String BEAN_NAME = "annotationChannelServiceFactory";

    private Table<ChannelEnum, ServiceTypeEnum, IChannelService> channelServiceTable = Tables
            .newCustomTable(new ConcurrentHashMap<>(), ConcurrentHashMap::new);

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public IChannelService getChannelService(ChannelEnum channel, ServiceTypeEnum serviceType) {
        IChannelService channelService = channelServiceTable.get(channel, serviceType);
        if (channelService == null) {
            log.error("获取通道服务失败，channel={}，serviceType={}", channel, serviceType);
        }
        return channelService;
    }

    public void initialization() {
        Collection<IChannelService> channelServices = applicationContext.getBeansOfType(IChannelService.class, false, false).values();

        channelServices.forEach(channelService -> {
            Class<? extends IChannelService> clazz = channelService.getClass();
            if (clazz.isAnnotationPresent(ChannelService.class)) {
                ChannelService service = clazz.getAnnotation(ChannelService.class);
                ChannelEnum channelEnum = service.channel();
                ServiceTypeEnum serviceTypeEnum = service.serviceTypeEnum();
                channelServiceTable.put(channelEnum, serviceTypeEnum, channelService);
                log.info("初始化通道服务，{} -> {}", channelEnum.getCode(), serviceTypeEnum.getId());
            }
        });

        log.info("通道服务工厂初始化完成，目前已实现：{}", Arrays.asList(this.channelServiceTable.columnMap().keySet().toArray()));
    }

}
