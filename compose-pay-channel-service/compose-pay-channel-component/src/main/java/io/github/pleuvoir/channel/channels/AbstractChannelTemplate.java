package io.github.pleuvoir.channel.channels;

import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.ChannelMerDTO;
import io.github.pleuvoir.channel.model.shared.AbstractReqModel;
import io.github.pleuvoir.channel.model.shared.AbstractRspModel;
import io.github.pleuvoir.pay.common.enums.ResultCodeEnum;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 三方支付调用模板方法
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public abstract class AbstractChannelTemplate<T extends AbstractReqModel, R extends AbstractRspModel> implements
        IChannelService<T, R> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public R invoke(T request) throws ChannelServiceException {

        //参数校验
        this.verify(request);

        //获取通道商户
        final ChannelMerDTO channelMerDTO = request.getChannelMerDTO();
        if (channelMerDTO == null) {
            throw new ChannelServiceException(ResultCodeEnum.NOT_FOUND_CHANNEL);
        }

        Map<String, String> params = this.generateParams(request, channelMerDTO);
        if (MapUtils.isEmpty(params)) {
            throw new ChannelServiceException(ResultCodeEnum.PARAM_ERROR, "通道请求参数为空");
        }

        final Map<String, String> response = this.doRequest(params, channelMerDTO);
        if (MapUtils.isEmpty(response)) {
            // 可能是调用三方有问题，也可能是系统问题
            throw new ChannelServiceException(ResultCodeEnum.ERROR, "请求通道返回空");
        }

        return this.convertResult(response, request, channelMerDTO);
    }

    /**
     * 参数校验
     */
    protected abstract void verify(T request);

    /**
     * 生成请求参数
     */
    protected abstract Map<String, String> generateParams(T t, ChannelMerDTO account);

    /**
     * 发起请求，网络通信
     */
    protected abstract Map<String, String> doRequest(Map<String, String> params, ChannelMerDTO account);

    /**
     * 转换请求结果
     */
    protected abstract R convertResult(Map<String, String> result, T t, ChannelMerDTO account);
}
