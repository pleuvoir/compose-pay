package io.github.pleuvoir.gateway.component;

import io.github.pleuvoir.gateway.agent.BasePayAgent;
import io.github.pleuvoir.gateway.model.dto.PaymentDTO;
import io.github.pleuvoir.gateway.model.vo.ResultBasePayVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pleuvoir
 * 
 */
@Slf4j
public class BasePayAgentImpl implements BasePayAgent {

	@Override
	public ResultBasePayVO pay(PaymentDTO dto) {
		log.info("【发起支付请求】，入参：{}", dto);
		return null;
	}

}
