package io.github.channel;

import io.github.pleuvoir.channel.common.ChannelEnum;
import io.github.pleuvoir.channel.common.ServiceIdEnum;
import io.github.pleuvoir.channel.plugin.DefaultChannelServiceFactory;
import io.github.pleuvoir.channel.plugin.IChannelService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class FactoryTest extends BaseTest {

    @Autowired
    private DefaultChannelServiceFactory channelServiceFactory;
    @Test
    public void test(){
        IChannelService channelService = channelServiceFactory.getChannelService(ChannelEnum.MOCK, ServiceIdEnum.PAY_QUERY);

        System.out.println("-=-=-=" + channelService);

        Assert.assertNotNull(channelService);
    }
}
