package io.github.pleuvoir.gateway.service;

import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.dto.QrCodePayRequestDTO;
import io.github.pleuvoir.gateway.model.vo.ResultBasePayVO;

/**
 * 扫码支付
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface IQrCodePayService {

    ResultBasePayVO qrCodePayUrl(QrCodePayRequestDTO payRequestDTO) throws BusinessException;
}
