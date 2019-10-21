package io.github.pleuvoir.agent;

import org.springframework.beans.factory.annotation.Autowired;

import io.github.pleuvoir.BaseTest;
import io.github.pleuvoir.gateway.agent.BasePayAgent;
import io.github.pleuvoir.gateway.model.dto.PaymentDTO;
import io.github.pleuvoir.gateway.model.vo.ResultBasePayVO;

/**
 * @author pleuvoir
 * 
 */
public class BasePayAgentTest extends BaseTest{

	
	@Autowired
	BasePayAgent agent;
	
	public void test() {
		PaymentDTO dto = new PaymentDTO();
		ResultBasePayVO pay = agent.pay(dto);
		
		System.out.println(pay.toJSON());
	}
}
