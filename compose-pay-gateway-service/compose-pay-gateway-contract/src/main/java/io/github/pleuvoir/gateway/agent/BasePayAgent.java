package io.github.pleuvoir.gateway.agent;

import io.github.pleuvoir.gateway.model.dto.PaymentDTO;
import io.github.pleuvoir.gateway.model.vo.ResultBasePayVO;

/**
 * 传统支付
 * @author pleuvoir
 * 
 */
public interface BasePayAgent {

	ResultBasePayVO pay(PaymentDTO dto);
}
