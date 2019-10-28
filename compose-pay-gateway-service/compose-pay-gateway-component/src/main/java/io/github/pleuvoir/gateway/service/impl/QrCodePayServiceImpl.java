package io.github.pleuvoir.gateway.service.impl;

import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.dto.PaymentDTO;
import io.github.pleuvoir.gateway.model.po.MerchantPO;
import io.github.pleuvoir.gateway.model.vo.ResultBasePayVO;
import io.github.pleuvoir.gateway.service.QrCodePayService;
import io.github.pleuvoir.gateway.service.internal.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 扫码支付
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Service
public class QrCodePayServiceImpl extends BaseServiceImpl implements QrCodePayService {

    @Override
    public ResultBasePayVO qrCodePayUrl(PaymentDTO paymentDTO) throws BusinessException {

        //检查商户并校验状态
        MerchantPO merchantPO = checkMerchant(paymentDTO.getMid());

        ResultBasePayVO basePayVO = new ResultBasePayVO();
        basePayVO.setOrderNo(merchantPO.getMerName());

        return basePayVO;
    }
}
