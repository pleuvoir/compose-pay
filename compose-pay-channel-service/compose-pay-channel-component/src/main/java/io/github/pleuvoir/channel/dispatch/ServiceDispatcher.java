package io.github.pleuvoir.channel.dispatch;

import com.alibaba.fastjson.JSON;
import io.github.pleuvoir.channel.channels.IChannelService;
import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.factory.IChannelServiceFactory;
import io.github.pleuvoir.channel.model.shared.AbstractReqModel;
import io.github.pleuvoir.channel.model.shared.AbstractRspModel;
import io.github.pleuvoir.pay.common.enums.ChannelEnum;
import io.github.pleuvoir.pay.common.enums.PayProductEnum;
import io.github.pleuvoir.pay.common.enums.ResultCodeEnum;
import io.github.pleuvoir.pay.common.enums.ServiceTypeEnum;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.stereotype.Component;

/**
 * 服务分发器
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Component
@Slf4j
public class ServiceDispatcher {

    @Resource
    private IChannelServiceFactory channelServiceFactory;

    private IChannelLifeCycleListener lifeCycleListener = new IChannelLifeCycleListener.Adapter();

    public <R extends AbstractRspModel> R doDispatch(AbstractReqModel reqModel,ServiceTypeEnum serviceType) throws ChannelServiceException {

        //获取服务类别
        if (serviceType == null) {
            log.error("未找到服务类别，reqModel={}", JSON.toJSONString(reqModel));
            throw new ChannelServiceException(ResultCodeEnum.ERROR);
        }

        //获取支付产品
        PayProductEnum productEnum = PayProductEnum.toPayProductEnum(reqModel.getPayProduct());
        if (productEnum == null) {
            throw new ChannelServiceException(ResultCodeEnum.NO_PRODUCT_PROVIDE);
        }

        //获取支付通道
        ChannelEnum channelEnum = productEnum.getChannel();
        if (channelEnum == null) {
            log.error("未找到通道，productEnum={}", productEnum.getCode());
            throw new ChannelServiceException(ResultCodeEnum.ERROR);
        }
        reqModel.setChannel(channelEnum);

        IChannelService channelService = channelServiceFactory.getChannelService(channelEnum, serviceType);

        if (channelService == null) {
            log.error("获取通道服务失败 :) channel={}，serviceType={}", JSON.toJSONString(channelEnum), serviceType);
            throw new ChannelServiceException(ResultCodeEnum.ERROR, "获取通道服务失败");
        }
        log.info("获取通道服务成功。channelService={}，serviceType={}", channelService.getClass().getSimpleName(),
                serviceType);

        lifeCycleListener.beforeRequest(reqModel);

        StopWatch watch = StopWatch.createStarted();
        R rspModel;
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
