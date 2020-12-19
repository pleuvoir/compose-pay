package io.github.pleuvoir.gateway.component;

import io.github.pleuvoir.gateway.agent.IPayAgent;
import io.github.pleuvoir.gateway.common.aop.Log;
import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.dto.CloseOrderDTO;
import io.github.pleuvoir.gateway.model.dto.CloseOrderResultDTO;
import io.github.pleuvoir.gateway.model.dto.PayQueryDTO;
import io.github.pleuvoir.gateway.model.dto.PayQueryResultDTO;
import io.github.pleuvoir.gateway.model.dto.PayRequestDTO;
import io.github.pleuvoir.gateway.model.dto.PayRequestResultDTO;
import io.github.pleuvoir.gateway.model.dto.RefundApplyDTO;
import io.github.pleuvoir.gateway.model.dto.RefundApplyResultDTO;
import io.github.pleuvoir.gateway.model.dto.RefundQueryDTO;
import io.github.pleuvoir.gateway.model.dto.RefundQueryResultDTO;
import io.github.pleuvoir.gateway.model.vo.Result;
import io.github.pleuvoir.gateway.service.ICloseOrderService;
import io.github.pleuvoir.gateway.service.IPayQueryService;
import io.github.pleuvoir.gateway.service.IPayService;
import io.github.pleuvoir.gateway.service.IRefundApplyService;
import io.github.pleuvoir.gateway.service.IRefundQueryService;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 对外支付接口，通过RPC调用。提供：
 *
 * <p>
 * 预支付 支付结果查询 退款 退款结果查询 订单关闭
 * </p>
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
@Component
public class PayAgentImpl implements IPayAgent {


    @Resource
    private IPayService payService;
    @Resource
    private IPayQueryService payQueryService;
    @Resource
    private IRefundApplyService refundApplyService;
    @Resource
    private IRefundQueryService refundQueryService;
    @Resource
    private ICloseOrderService closeOrderService;


    @Log("预支付接口")
    @Override
    public Result<PayRequestResultDTO> pay(@Valid PayRequestDTO payRequestDTO) {
        try {
            PayRequestResultDTO resultDTO = payService.pay(payRequestDTO);
            return Result.success(resultDTO);
        } catch (BusinessException e) {
            log.info("pay - 预支付接口 异常 in param={}，exception={}", payRequestDTO.toJSON(), e);
            return Result.fail(e.getResultCode());
        }
    }

    @Log("支付结果查询")
    @Override
    public Result<PayQueryResultDTO> payQuery(@Valid PayQueryDTO payQueryDTO) {
        try {
            PayQueryResultDTO resultDTO = payQueryService.payQuery(payQueryDTO);
            return Result.success(resultDTO);
        } catch (BusinessException e) {
            log.info("payQuery - 支付结果查询 异常 in param={}，exception={}", payQueryDTO.toJSON(), e);
            return Result.fail(e.getResultCode());
        }
    }

    @Log("退款申请")
    @Override
    public Result<RefundApplyResultDTO> refundApply(@Valid RefundApplyDTO refundApplyDTO) {
        try {
            RefundApplyResultDTO resultDTO = refundApplyService.refundApply(refundApplyDTO);
            return Result.success(resultDTO);
        } catch (BusinessException e) {
            log.info("refundApply - 退款申请 异常 in param={}，exception={}", refundApplyDTO.toJSON(), e);
            return Result.fail(e.getResultCode());
        }
    }

    @Log("退款结果查询")
    @Override
    public Result<List<RefundQueryResultDTO>> refundQuery(@Valid RefundQueryDTO refundQueryDTO) {
        try {
            List<RefundQueryResultDTO> resultDTOS = refundQueryService.refundQuery(refundQueryDTO);
            return Result.success(resultDTOS);
        } catch (BusinessException e) {
            log.info("refundQuery - 退款结果查询 异常 in param={}，exception={}", refundQueryDTO.toJSON(), e);
            return Result.fail(e.getResultCode());
        }
    }

    @Log("关闭订单")
    @Override
    public Result<CloseOrderResultDTO> closeOrder(@Valid CloseOrderDTO closeOrderDTO) {
        try {
            CloseOrderResultDTO resultDTO = closeOrderService.closeOrder(closeOrderDTO);
            return Result.success(resultDTO);
        } catch (BusinessException e) {
            log.info("closeOrder - 关闭订单 异常 in param={}，exception={}", closeOrderDTO.toJSON(), e);
            return Result.fail(e.getResultCode());
        }
    }
}
