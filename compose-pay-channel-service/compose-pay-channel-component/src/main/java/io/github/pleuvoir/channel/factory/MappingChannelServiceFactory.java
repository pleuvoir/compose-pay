package io.github.pleuvoir.channel.factory;

import io.github.pleuvoir.channel.channels.IChannelService;
import io.github.pleuvoir.channel.channels.mock.MockPay;
import io.github.pleuvoir.channel.common.util.ApplicationContextHelper;
import io.github.pleuvoir.pay.common.enums.ChannelEnum;
import io.github.pleuvoir.pay.common.enums.ServiceTypeEnum;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 枚举映射通道服务工厂
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Slf4j
public class MappingChannelServiceFactory implements IChannelServiceFactory {

    enum Mapping {

        MOCK(ChannelEnum.MOCK, ServiceTypeEnum.PAY, MockPay.class);

        @Getter
        private ChannelEnum channel;
        @Getter
        private ServiceTypeEnum serviceType;
        @Getter
        private Class<? extends IChannelService> channelService;


        Mapping(ChannelEnum channel, ServiceTypeEnum serviceType, Class<? extends IChannelService> channelService) {
            this.channel = channel;
            this.serviceType = serviceType;
            this.channelService = channelService;
        }

        public static Class<? extends IChannelService> mapping(ChannelEnum channel, ServiceTypeEnum serviceType) {
            if (channel == null || serviceType == null) {
                return null;
            }
            for (Mapping mapping : Mapping.values()) {
                if (mapping.getChannel().equals(channel) && mapping.getServiceType().equals(serviceType)) {
                    return mapping.getChannelService();
                }
            }
            return null;
        }
    }


    @Override
    public IChannelService getChannelService(ChannelEnum channel, ServiceTypeEnum serviceType) {
        Class<? extends IChannelService> clazz = Mapping.mapping(channel, serviceType);
        if (clazz == null) {
            log.error("获取通道服务失败，枚举未配置，channel={}，serviceType={}", channel, serviceType);
            return null;
        }
        return ApplicationContextHelper.getBean(clazz);
    }


}
