package io.github.pleuvoir.channel.dispatch;

import com.alibaba.fastjson.JSON;
import io.github.pleuvoir.channel.common.ChannelEnum;
import io.github.pleuvoir.channel.common.ReturnCodeEnum;
import io.github.pleuvoir.channel.common.ServiceIdEnum;
import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.shared.AbstractReqModel;
import io.github.pleuvoir.channel.model.shared.AbstractRspModel;
import io.github.pleuvoir.channel.channels.IChannelService;
import io.github.pleuvoir.channel.factory.IChannelServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 服务分发器
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Component
@Slf4j
public class ServiceDispatcher {

    @Autowired
    private IChannelServiceFactory channelServiceFactory;

    private IChannelLifeCycleListener lifeCycleListener = new IChannelLifeCycleListener.Adapter();

    public <R extends AbstractRspModel> R doDispatch(AbstractReqModel reqModel) throws ChannelServiceException {
        final ChannelEnum channel = reqModel.getChannel();
        final ServiceIdEnum serviceId = reqModel.getServiceId();
        IChannelService channelService = channelServiceFactory.getChannelService(channel, serviceId);

        if (channelService == null) {
            log.error("获取通道服务失败 :) channel={}，serviceId={}", JSON.toJSONString(channel), JSON.toJSONString(serviceId));
            throw new ChannelServiceException(ReturnCodeEnum.ERROR, "获取通道服务失败");
        }
        log.info("获取通道服务成功。channelService={}，serviceId={}", channelService.getClass().getSimpleName(),
                JSON.toJSONString(serviceId));


        lifeCycleListener.beforeRequest(reqModel);

        StopWatch watch = StopWatch.createStarted();
        R rspModel = null;
        try {
            rspModel = (R) channelService.invoke(reqModel);
        } catch (ChannelServiceException e) {
            lifeCycleListener.exceptionCaught(e);
            throw e;
        }
        watch.stop();

        lifeCycleListener.afterRequest(reqModel, rspModel);

        log.info("调用通道服务成功，耗时{}（毫秒）。reqModel={}，rspModel={}",
                watch.getTime(TimeUnit.MILLISECONDS),
                JSON.toJSONString(reqModel),
                JSON.toJSONString(rspModel));

        return rspModel;
    }

}
