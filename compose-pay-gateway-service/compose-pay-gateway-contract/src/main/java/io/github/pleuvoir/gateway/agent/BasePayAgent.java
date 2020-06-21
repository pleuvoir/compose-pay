package io.github.pleuvoir.gateway.agent;

import io.github.pleuvoir.gateway.model.dto.QrCodePayRequestDTO;
import io.github.pleuvoir.gateway.model.dto.QrCodePayResultDTO;
import io.github.pleuvoir.gateway.model.vo.ResultBasePayVO;
import io.github.pleuvoir.gateway.model.vo.ResultMessageVO;

/**
 * 传统支付
 *
 * @author pleuvoir
 */
public interface BasePayAgent {

    ResultMessageVO<QrCodePayResultDTO> payCode(QrCodePayRequestDTO dto);
}
