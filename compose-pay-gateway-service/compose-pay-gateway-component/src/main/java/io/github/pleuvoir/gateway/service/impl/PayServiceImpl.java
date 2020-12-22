/*
 * Copyright © 2020 pleuvoir (pleuvior@foxmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.pleuvoir.gateway.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import io.github.pleuvoir.channel.agent.IStdChannelServiceAgent;
import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.request.PaymentDTO;
import io.github.pleuvoir.channel.model.response.PaymentResultDTO;
import io.github.pleuvoir.gateway.common.utils.PayIdUtils;
import io.github.pleuvoir.gateway.model.dto.PayRequestDTO;
import io.github.pleuvoir.gateway.model.dto.PayRequestResultDTO;
import io.github.pleuvoir.gateway.model.po.MerChannelPO;
import io.github.pleuvoir.gateway.model.po.MerPayPO;
import io.github.pleuvoir.gateway.model.po.MerchantPO;
import io.github.pleuvoir.gateway.service.IPayService;
import io.github.pleuvoir.gateway.service.ITransactionService;
import io.github.pleuvoir.gateway.service.internal.IMerIpService;
import io.github.pleuvoir.gateway.service.internal.IMerPayService;
import io.github.pleuvoir.gateway.service.internal.impl.BaseServiceImpl;
import io.github.pleuvoir.gateway.utils.AssertUtil;
import io.github.pleuvoir.pay.common.enums.ChannelServiceIdMappingEnum;
import io.github.pleuvoir.pay.common.enums.ResultCodeEnum;
import io.github.pleuvoir.pay.common.exception.BusinessException;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
    private ITransactionService transactionService;
    @Resource
    private IMerPayService merPayService;
    @Resource
    private IMerIpService merIpService;

    @Override
    public PayRequestResultDTO pay(PayRequestDTO payRequestDTO) throws BusinessException {


        //检查商户并校验状态
        MerchantPO merchantPO = checkMerchant(payRequestDTO.getMid());

        //检查商户入口IP是否绑定
        merIpService.ipLimit(payRequestDTO.getMid(), payRequestDTO.getIp());

        //检查是否已有存在的唯一流水号
        MerPayPO prev = merPayService.getByTransUniqueId(payRequestDTO.getTransUniqueId());
        if (Objects.nonNull(prev)) {
            throw new BusinessException(ResultCodeEnum.NOT_FOUND_CHANNEL_MID);
        }

        //渠道路由
        MerChannelPO merChannelPO = null;
        if (merChannelPO == null) {
            throw new BusinessException(ResultCodeEnum.TRADE_ALREADY_EXIST);
        }
        
        

        long serialNo = PayIdUtils.getSerialNo(payRequestDTO.getTransUniqueId());
        //创建支付订单
        MerPayPO merPayPO = this.installMerPayPO(payRequestDTO, merChannelPO, serialNo);
        Integer ret = merPayService.save(merPayPO);
        AssertUtil.assertOne(ret, "创建支付订单失败");

        ChannelServiceIdMappingEnum mappingEnum = ChannelServiceIdMappingEnum.toEnum(payRequestDTO.getMappingCode());
        if(mappingEnum == null){
            throw new BusinessException(ResultCodeEnum.NO_FUNCTION_PROVIDE);
        }

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setChannel(mappingEnum.getChannelEnum());
        paymentDTO.setServiceId(mappingEnum.getServiceIdEnum());


        try {
            log.info("请求通道服务，预支付 入参 paymentDTO：{}", JSON.toJSONString(paymentDTO));
            PaymentResultDTO resultDTO = channelServiceAgent.payOrder(paymentDTO);
            log.info("请求通道服务，预支付 入参 paymentDTO：{}，响应 resultDTO：{}", JSON.toJSONString(paymentDTO)
                    , JSON.toJSONString(resultDTO));

            //构建返回结果
            PayRequestResultDTO requestResultDTO = new PayRequestResultDTO();
            requestResultDTO.setMid(payRequestDTO.getMid());
            requestResultDTO.setTransUniqueId(payRequestDTO.getTransUniqueId());
            requestResultDTO.setSerialNo(serialNo);
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


    private MerPayPO installMerPayPO(PayRequestDTO payRequestDTO, MerChannelPO merChannelPO, long serialNo) {
        MerPayPO payPO = new MerPayPO();
        payPO.setSerialNo(serialNo);  //保证两个分到一张表中
        payPO.setTransUniqueId(payRequestDTO.getTransUniqueId()); //保证两个分到一张表中
        payPO.setOrderNo(payRequestDTO.getOrderNo());
        payPO.setPayStatus(MerPayPO.PAY_STATUS_WAIT);
        payPO.setSubject(payRequestDTO.getSubject());
        payPO.setBody(payRequestDTO.getBody());
        payPO.setTotalAmount(payRequestDTO.getAmount());
        payPO.setChannelCode(merChannelPO.getChannelCode());
        payPO.setChannelMid(merChannelPO.getChannelMid());
        return payPO;
    }

}
