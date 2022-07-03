package io.github.channel;

import io.github.pleuvoir.channel.channels.IChannelService;
import io.github.pleuvoir.channel.factory.PluginChannelServiceFactory;
import io.github.pleuvoir.pay.common.enums.ChannelEnum;
import io.github.pleuvoir.pay.common.enums.ServiceTypeEnum;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class FactoryTest extends BaseTest {

    @Resource
    private PluginChannelServiceFactory channelServiceFactory;

    @Test
    public void test(){
        IChannelService channelService = channelServiceFactory.getChannelService(ChannelEnum.MOCK, ServiceTypeEnum.PAY_QUERY);
        System.out.println("-=-=-=" + channelService);
        Assert.assertNotNull(channelService);
    }
}
