package io.github.pleuvoir.gateway.route;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.github.pleuvoir.gateway.constants.PayWayEnum;
import io.github.pleuvoir.gateway.constants.RspCodeEnum;
import io.github.pleuvoir.gateway.dao.mer.MerChannelDao;
import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.po.MerChannelPO;
import io.github.pleuvoir.gateway.model.po.MerSignFeePO;
import io.github.pleuvoir.gateway.service.internal.MerChannelService;
import io.github.pleuvoir.gateway.service.internal.MerSignFeeService;
import io.github.pleuvoir.gateway.service.internal.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 路由服务
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private MerChannelService merChannelService;
    @Autowired
    private MerSignFeeService signFeeService;

    @Override
    public MerChannelPO find(String mid, String payType, String payWay) throws BusinessException {

        log.info("-=- 渠道路由开始，mid={}，payType={}，payWay={}", mid, payType, payWay);

        //获取商户签约的支付产品
        final MerSignFeePO merSignFeePO = signFeeService.getMerSignsByMidAndPayWayAndPayType(mid, payType, payWay);
        if (merSignFeePO == null) {
            logException(mid, payType, payWay, RspCodeEnum.MER_UN_SIGN_ERROR.getMsg());
            throw new BusinessException(RspCodeEnum.MER_UN_SIGN_ERROR);
        }

        //获取商户通道关系
        MerChannelPO merChannelPO = merChannelService.maxPriority(mid, payType, payWay);
        if (merChannelPO == null) {
            logException(mid, payType, payWay, RspCodeEnum.NOT_FOUND_CHANNEL_MID.getMsg());
            throw new BusinessException(RspCodeEnum.NOT_FOUND_CHANNEL_MID);
        }

        //获取商户通道支付产品


        log.info("-=- 渠道路由结束，mid={}，payType={}，payWay={}", mid, payType, payWay);

        return merChannelPO;
    }


    void logException(String mid, String payType, String payWay, String message) {
        log.warn("-=- 渠道路由异常<{}>，mid={}，payType={}，payWay={}", message, mid, payType, payWay);
    }

}
