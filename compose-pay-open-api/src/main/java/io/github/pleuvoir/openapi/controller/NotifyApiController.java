package io.github.pleuvoir.openapi.controller;

import cn.xdf.gateway.common.Const;
import cn.xdf.gateway.common.exception.GatewayServiceException;
import cn.xdf.gateway.common.utils.HttpParamUtil;
import cn.xdf.gateway.service.INotifyChannelService;
import cn.xdf.pay.model.gateway.request.NotifyParamDTO;
import cn.xdf.pay.model.gateway.response.ChannelCommonNotifyResponseDTO;
import cn.xdf.pay.util.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通道通知入口
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Slf4j
@RestController
@RequestMapping("/external/notify")
@SuppressWarnings("all")
public class NotifyApiController {


    @Resource
    private INotifyChannelService notifyChannelService;

    /**
     * 支付通知
     * <br> 处理通道编号的支付回调
     */
    @ApiOperation("支付通知")
    @RequestMapping(value = "/pay/paychannel/{paychannel}/{serialno}", method =
            {RequestMethod.GET, RequestMethod.POST})
    public void channelNotify(@PathVariable("paychannel") String paychannel, @PathVariable("serialno") String serialNo,
            HttpServletRequest request, HttpServletResponse response) {

        try {
            NotifyParamDTO notifyParam = HttpParamUtil.toNotifyParam(request);
            notifyParam.putAttach(Const.SERIAL_NO, serialNo);
            notifyParam.putAttach(Const.PAY_CHANNEL_CODE, paychannel);

            final String receive = notifyChannelService.paidReceive(notifyParam);

            responseToChannel(HttpStatus.OK, response, receive);
            return;

        } catch (IOException e) {
            log.error("获取通知参数失败，paychannel={}", paychannel);
        } catch (GatewayServiceException e) {
            LogUtil.infoService("channelNotify", "通知异常", e.getMsg());
        }

        responseToChannel(HttpStatus.INTERNAL_SERVER_ERROR, response, ChannelCommonNotifyResponseDTO.failReturn());
    }


    @ApiOperation("支付通知补偿")
    @RequestMapping(value = "/retry-paid-receive", method = {RequestMethod.POST})
    public void retryPaidReceive(@RequestBody NotifyParamDTO notifyParam, HttpServletResponse response) {
        try {
            final String receive = notifyChannelService.paidReceive(notifyParam);
            responseToChannel(HttpStatus.OK, response, receive);
            return;

        } catch (GatewayServiceException e) {
            LogUtil.infoService("retryPaidReceive", "通知异常", e.getMsg());
        }
        responseToChannel(HttpStatus.OK, response, ChannelCommonNotifyResponseDTO.failReturn());
    }

    /**
     * 退款通知
     */
    @ApiOperation("退款通知")
    @RequestMapping(value = "/refund/bankid/{bankid}", method =
            {RequestMethod.GET, RequestMethod.POST})
    public void refundNotify(@PathVariable("bankid") String bankId,
            HttpServletRequest request, HttpServletResponse response) {

        try {
            NotifyParamDTO notifyParam = HttpParamUtil.toNotifyParam(request);
            notifyParam.putAttach(Const.BANK_ID, bankId);
            String receive = notifyChannelService.refundReceive(notifyParam);
            responseToChannel(HttpStatus.OK, response, receive);
            return;
        } catch (IOException e) {
            log.error("获取通知参数失败，bankId={}", bankId);
        } catch (GatewayServiceException e) {
            LogUtil.infoService("refundNotify", "通知异常", e.getMsg());
        }
        responseToChannel(HttpStatus.INTERNAL_SERVER_ERROR, response, ChannelCommonNotifyResponseDTO.failReturn());
    }


    private void responseToChannel(HttpStatus httpStatus, HttpServletResponse response, String result) {
        try {
            response.setStatus(httpStatus.value());
            response.setHeader("Content-type", "text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(result);
        } catch (IOException e) {
            log.error("接收通知，响应三方通道失败：" + result, e);
        }
    }

}
