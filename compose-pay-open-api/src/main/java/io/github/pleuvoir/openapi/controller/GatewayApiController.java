package io.github.pleuvoir.openapi.controller;

import io.github.pleuvoir.gateway.model.dto.PaymentDTO;
import io.github.pleuvoir.openapi.model.dto.ResultMessageDTO;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 网关开放入口
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@SuppressWarnings("all")
@Slf4j
@RestController
@RequestMapping("/gateway")
public class GatewayApiController {


    @Resource
    private IPayAgent agent;


    /**
     * 统一下单
     */
    @RequestMapping(value = "/unifiedorder", method = RequestMethod.POST)
    public ResultMessageDTO<PaymentResultDTO> unifiedorder(@RequestBody PaymentDTO paymentDTO) {
        try {
            final PaymentResultDTO resultDTO = agent.payOrder(paymentDTO);
            return ResultMessageDTO.dataOf(resultDTO);
        } catch (GatewayServiceException e) {
            return ResultMessageDTO.errorOf(e.getCode(), e.getMsg());
        }
    }


    /**
     * 订单查询
     */
    @RequestMapping(value = "/orderquery", method = RequestMethod.POST)
    public ResultMessageDTO<PayQueryResultDTO> orderquery(@RequestBody PayQueryDTO queryDTO) {
        try {
            final PayQueryResultDTO resultDTO = agent.payQuery(queryDTO);
            return ResultMessageDTO.dataOf(resultDTO);
        } catch (GatewayServiceException e) {
            return ResultMessageDTO.errorOf(e.getCode(), e.getMsg());
        }
    }


    /**
     * 退款申请
     */
    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public ResultMessageDTO<RefundResultDTO> refund(@RequestBody RefundDTO refundDTO) {
        try {
            final RefundResultDTO resultDTO = agent.refund(refundDTO);
            return ResultMessageDTO.dataOf(resultDTO);
        } catch (GatewayServiceException e) {
            return ResultMessageDTO.errorOf(e.getCode(), e.getMsg());
        }
    }


    /**
     * 退款结果查询
     */
    @RequestMapping(value = "/refundquery", method = RequestMethod.POST)
    public ResultMessageDTO<RefundQueryResultDTO> refundquery(@RequestBody RefundQueryDTO queryDTO) {
        try {
            final RefundQueryResultDTO resultDTO = agent.refundQuery(queryDTO);
            return ResultMessageDTO.dataOf(resultDTO);
        } catch (GatewayServiceException e) {
            return ResultMessageDTO.errorOf(e.getCode(), e.getMsg());
        }
    }


}
