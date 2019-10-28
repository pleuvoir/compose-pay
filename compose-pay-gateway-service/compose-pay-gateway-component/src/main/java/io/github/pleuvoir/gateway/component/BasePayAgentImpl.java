package io.github.pleuvoir.gateway.component;

import com.alibaba.fastjson.JSON;
import io.github.pleuvoir.gateway.agent.BasePayAgent;
import io.github.pleuvoir.gateway.common.aop.MethodTimeLog;
import io.github.pleuvoir.gateway.constants.PayTypeEnum;
import io.github.pleuvoir.gateway.constants.RspCodeEnum;
import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.dto.PaymentDTO;
import io.github.pleuvoir.gateway.model.vo.ResultBasePayVO;
import io.github.pleuvoir.gateway.model.vo.ResultMessageVO;
import io.github.pleuvoir.gateway.service.QrCodePayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

/**
 * @author pleuvoir
 */
@Slf4j
@Service
public class BasePayAgentImpl implements BasePayAgent {

    @Autowired
    private QrCodePayService qrCodePayService;

    @MethodTimeLog("获取支付二维码")
    @Override
    public ResultMessageVO<ResultBasePayVO> payCode(@Valid PaymentDTO dto) {

        if (!this.validatePayType(dto.getType())) {
            return ResultMessageVO.fail(RspCodeEnum.INVALID_PAYTYPE);
        }

        ResultBasePayVO result = null;
        try {
            result = qrCodePayService.qrCodePayUrl(dto);
            if (result != null) {
                log.info("获取支付二维码响应参数：{}", result);
            } else {
                log.error("获取二维码响应参数：null，请求信息：{}", JSON.toJSONString(dto));
                return ResultMessageVO.fail(RspCodeEnum.ERROR);
            }
        } catch (BusinessException e) {
            log.warn("获取支付二维码业务异常，{}", e.getMessage());
            return ResultMessageVO.fail(e.getResultCode());
        }

        return ResultMessageVO.success(result);
    }

    /**
     * 判断支付方式是否正确
     */
    private boolean validatePayType(String payType) {
        if (StringUtils.equals(PayTypeEnum.TYPE_ALIPAY.getName(), payType)
                || StringUtils.equals(PayTypeEnum.TYPE_WECHAT.getName(), payType)) {
            return true;
        }
        return false;
    }
}
