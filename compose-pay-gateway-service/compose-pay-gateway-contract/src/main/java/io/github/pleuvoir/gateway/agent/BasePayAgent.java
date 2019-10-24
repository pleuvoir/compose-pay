package io.github.pleuvoir.gateway.agent;

import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.dto.PaymentDTO;
import io.github.pleuvoir.gateway.model.vo.ResultBasePayVO;
import io.github.pleuvoir.gateway.model.vo.ResultMessageVO;

/**
 * 传统支付
 *
 * @author pleuvoir
 */
public interface BasePayAgent {

    ResultMessageVO<ResultBasePayVO> pay(PaymentDTO dto);
}
