package io.github.channel;

import io.github.pleuvoir.channel.channels.IChannelService;
import io.github.pleuvoir.channel.factory.DefaultChannelServiceFactory;
import io.github.pleuvoir.pay.common.enums.ChannelEnum;
import io.github.pleuvoir.pay.common.enums.ServiceIdEnum;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class FactoryTest extends BaseTest {

    @Resource
    private DefaultChannelServiceFactory channelServiceFactory;
    @Test
    public void test(){
        IChannelService channelService = channelServiceFactory.getChannelService(ChannelEnum.MOCK, ServiceIdEnum.PAY_QUERY);

        System.out.println("-=-=-=" + channelService);

        Assert.assertNotNull(channelService);
    }
}
