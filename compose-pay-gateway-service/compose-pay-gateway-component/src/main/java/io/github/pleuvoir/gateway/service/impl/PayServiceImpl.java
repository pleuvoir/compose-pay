package io.github.pleuvoir.gateway.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import io.github.pleuvoir.channel.agent.IStdChannelServiceAgent;
import io.github.pleuvoir.channel.common.ChannelEnum;
import io.github.pleuvoir.channel.common.ServiceIdEnum;
import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.request.PaymentDTO;
import io.github.pleuvoir.channel.model.response.PaymentResultDTO;
import io.github.pleuvoir.gateway.common.utils.IdUtils;
import io.github.pleuvoir.gateway.common.utils.PayIdUtils;
import io.github.pleuvoir.gateway.constants.PayTypeEnum;
import io.github.pleuvoir.gateway.constants.PayWayEnum;
import io.github.pleuvoir.gateway.constants.ResultCodeEnum;
import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.dto.PayRequestDTO;
import io.github.pleuvoir.gateway.model.dto.PayRequestResultDTO;
import io.github.pleuvoir.gateway.model.po.MerChannelPO;
import io.github.pleuvoir.gateway.model.po.MerPayPO;
import io.github.pleuvoir.gateway.model.po.MerchantPO;
import io.github.pleuvoir.gateway.route.RouteService;
import io.github.pleuvoir.gateway.service.IPayService;
import io.github.pleuvoir.gateway.service.ITransactionService;
import io.github.pleuvoir.gateway.service.internal.impl.BaseServiceImpl;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 预支付服务
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Service
@Slf4j
public class PayServiceImpl extends BaseServiceImpl implements IPayService {

    @Reference(version = "${dubbo.service.channel}")
    private IStdChannelServiceAgent channelServiceAgent;
    @Resource
    private RouteService routeService;
    @Resource
    private ITransactionService transactionService;

    @Override
    public PayRequestResultDTO pay(PayRequestDTO payRequestDTO) throws BusinessException {

        // 判断支付种类是否正确
        if (!this.validatePayType(payRequestDTO.getPayType())) {
            throw new BusinessException(ResultCodeEnum.INVALID_PAY_TYPE);
        }

        PayTypeEnum payTypeEnum = PayTypeEnum.toEnum(payRequestDTO.getPayType());
        if (payTypeEnum == null) {
            throw new BusinessException(ResultCodeEnum.INVALID_PAY_TYPE);
        }

        //检查商户并校验状态
        MerchantPO merchantPO = checkMerchant(payRequestDTO.getMid());

        //渠道路由
        MerChannelPO merChannelPO = routeService.find(merchantPO.getMid(), payTypeEnum.getCode(), PayWayEnum.SCAN_CODE.getCode());
        if (merChannelPO == null) {
            throw new BusinessException(ResultCodeEnum.NOT_FOUND_CHANNEL_MID);
        }

        long payId = PayIdUtils.getPayId(payRequestDTO.getTransUniqueId());
        //创建支付订单
        MerPayPO merPayPO = this.installMerPayPO(payRequestDTO, merChannelPO,payId);
        MerPayPO order = transactionService.createOrder(merPayPO, merchantPO);

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setChannel(ChannelEnum.toEnum(merChannelPO.getChannelCode().toString()));
        paymentDTO.setServiceId(ServiceIdEnum.SCAN_CODE);

        try {
            log.info("请求通道服务，预支付 入参 paymentDTO：{}", JSON.toJSONString(paymentDTO));
            PaymentResultDTO resultDTO = channelServiceAgent.payOrder(paymentDTO);
            log.info("请求通道服务，预支付 入参 paymentDTO：{}，响应 resultDTO：{}", JSON.toJSONString(paymentDTO)
                    , JSON.toJSONString(resultDTO));

            //构建返回结果
            PayRequestResultDTO requestResultDTO = new PayRequestResultDTO();
            requestResultDTO.setMid(payRequestDTO.getMid());
            requestResultDTO.setTransUniqueId(payRequestDTO.getTransUniqueId());
            requestResultDTO.setPayId(payId);
            requestResultDTO.setParamStr("");
            requestResultDTO.setPaySuccessUrl(payRequestDTO.getPaySuccessUrl());

            return requestResultDTO;

        } catch (ChannelServiceException e) {
            log.info("请求通道服务异常，预支付 入参 paymentDTO={}，msg={}", JSON.toJSONString(paymentDTO), e.getMsg());
            throw new BusinessException(ResultCodeEnum.CHANNEL_SERVICE_EXCEPTION);
        } catch (RpcException e) {
            log.error("请求通道服务异常，预支付 远程调用失败。入参：{}", JSON.toJSONString(paymentDTO), e);
            throw new BusinessException(ResultCodeEnum.CHANNEL_SERVICE_EXCEPTION);
        } catch (Throwable e) {
            log.error("请求通道服务失败，预支付 调用失败，未知原因。入参：{}", JSON.toJSONString(paymentDTO), e);
            throw new BusinessException(ResultCodeEnum.ERROR);
        }
    }


    private MerPayPO installMerPayPO(PayRequestDTO payRequestDTO, MerChannelPO merChannelPO,long payId) {
        MerPayPO payPO = new MerPayPO();
        payPO.setId(payId);
        payPO.setSerialNo(1L);  //保证两个分到一张表中
        payPO.setTransUniqueId(payRequestDTO.getTransUniqueId()); //保证两个分到一张表中
        payPO.setOrderNo(payRequestDTO.getOrderNo());
        payPO.setPayType(payRequestDTO.getPayType());
        payPO.setPayWay(payRequestDTO.getPayWay());
        payPO.setPayScene(null);
        payPO.setPayStatus(MerPayPO.PAY_STATUS_WAIT);
        payPO.setRefundStatus(MerPayPO.REFUND_STATUS_INIT);
        payPO.setSubject(payRequestDTO.getSubject());
        payPO.setBody(payRequestDTO.getBody());
        payPO.setTotalAmount(payRequestDTO.getAmount());
        payPO.setChannelCode(merChannelPO.getChannelCode());
        payPO.setChannelMid(merChannelPO.getChannelMid());
        return payPO;
    }


    /**
     * 判断支付种类是否正确
     */
    private boolean validatePayType(String payType) {
        return StringUtils.equals(PayTypeEnum.TYPE_ALIPAY.getName(), payType)
                || StringUtils.equals(PayTypeEnum.TYPE_WECHAT.getName(), payType);
    }
}
