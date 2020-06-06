package io.github.pleuvoir.gateway.service.impl;

import io.github.pleuvoir.gateway.constants.PayTypeEnum;
import io.github.pleuvoir.gateway.constants.PayWayEnum;
import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.dto.PaymentDTO;
import io.github.pleuvoir.gateway.model.po.MerChannelPO;
import io.github.pleuvoir.gateway.model.po.MerSignFeePO;
import io.github.pleuvoir.gateway.model.po.MerchantPO;
import io.github.pleuvoir.gateway.model.vo.ResultBasePayVO;
import io.github.pleuvoir.gateway.route.RouteService;
import io.github.pleuvoir.gateway.service.QrCodePayService;
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
public class QrCodePayServiceImpl extends BaseServiceImpl implements QrCodePayService {

    @Autowired
    private RouteService routeService;

    @Override
    public ResultBasePayVO qrCodePayUrl(PaymentDTO paymentDTO) throws BusinessException {

        //检查商户并校验状态
        MerchantPO merchantPO = checkMerchant(paymentDTO.getMid());

        PayTypeEnum payTypeEnum = PayTypeEnum.getThisByName(paymentDTO.getType());

        //渠道路由
        MerChannelPO merChannelPO = routeService.find(merchantPO.getMid(), payTypeEnum.getCode(), PayWayEnum.SCAN_CODE.getCode());

        //创建订单

        ResultBasePayVO basePayVO = new ResultBasePayVO();
        basePayVO.setOrderNo(merchantPO.getMerName());

        return basePayVO;
    }
}
