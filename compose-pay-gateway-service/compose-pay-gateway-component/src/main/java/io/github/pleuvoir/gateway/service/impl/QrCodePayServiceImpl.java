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
import io.github.pleuvoir.channel.common.ChannelEnum;
import io.github.pleuvoir.channel.common.ServiceIdEnum;
import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.channel.model.request.PaymentDTO;
import io.github.pleuvoir.channel.model.response.PaymentResultDTO;
import io.github.pleuvoir.gateway.common.utils.IdUtils;
import io.github.pleuvoir.gateway.constants.PayTypeEnum;
import io.github.pleuvoir.gateway.constants.PayWayEnum;
import io.github.pleuvoir.gateway.constants.ResultCodeEnum;
import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.dto.QrCodePayRequestDTO;
import io.github.pleuvoir.gateway.model.dto.QrCodePayResultDTO;
import io.github.pleuvoir.gateway.model.po.MerChannelPO;
import io.github.pleuvoir.gateway.model.po.MerPayPO;
import io.github.pleuvoir.gateway.model.po.MerchantPO;
import io.github.pleuvoir.gateway.route.RouteService;
import io.github.pleuvoir.gateway.service.IQrCodePayService;
import io.github.pleuvoir.gateway.service.ITransactionService;
import io.github.pleuvoir.gateway.service.internal.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 扫码支付
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Service
@Slf4j
public class QrCodePayServiceImpl extends BaseServiceImpl implements IQrCodePayService {

    @Reference(version = "${dubbo.service.channel}")
    private IStdChannelServiceAgent channelServiceAgent;

    @Autowired
    private RouteService routeService;
    @Autowired
    private ITransactionService transactionService;

    @Override
    public QrCodePayResultDTO qrCodePayUrl(QrCodePayRequestDTO payRequestDTO) throws BusinessException {

        //检查商户并校验状态
        MerchantPO merchantPO = checkMerchant(payRequestDTO.getMid());

        PayTypeEnum payTypeEnum = PayTypeEnum.toEumByName(payRequestDTO.getPayType());
        if (payTypeEnum == null) {
            throw new BusinessException(ResultCodeEnum.INVALID_PAY_TYPE);
        }

        //渠道路由
        MerChannelPO merChannelPO = routeService.find(merchantPO.getMid(), payTypeEnum.getCode(), PayWayEnum.SCAN_CODE.getCode());
        if (merChannelPO == null) {
            throw new BusinessException(ResultCodeEnum.NOT_FOUND_CHANNEL_MID);
        }

        //创建订单
        MerPayPO merPayPO = this.installMerPayPO(payRequestDTO,merChannelPO);
        MerPayPO order = transactionService.createOrder(merPayPO, merchantPO);

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setChannel(ChannelEnum.toEnumByCode(merChannelPO.getChannelCode()));
        paymentDTO.setServiceId(ServiceIdEnum.SCAN_CODE);

        try {
            log.info("请求通道服务，获取二维码 入参 paymentDTO：{}", JSON.toJSONString(paymentDTO));
            PaymentResultDTO resultDTO = channelServiceAgent.payOrder(paymentDTO);
            log.info("请求通道服务，获取二维码 入参 paymentDTO：{}，响应 resultDTO：{}", JSON.toJSONString(paymentDTO)
                    , JSON.toJSONString(resultDTO));

        } catch (ChannelServiceException e) {
            log.info("请求通道服务异常，获取二维码 入参 paymentDTO={}，msg={}", JSON.toJSONString(paymentDTO),
                    e.getMsg());
        } catch (RpcException e) {
            log.error("获取二维码失败，远程调用失败。入参：{}", JSON.toJSONString(paymentDTO), e);
        } catch (Throwable e) {
            log.error("获取二维码失败，调用失败，未知原因。入参：{}", JSON.toJSONString(paymentDTO), e);
        }

        return null;
    }


    private MerPayPO installMerPayPO(QrCodePayRequestDTO payRequestDTO, MerChannelPO merChannelPO) {
        MerPayPO payPO = new MerPayPO();
        payPO.setId(IdUtils.nextId());
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

}
