package io.github.pleuvoir.agent;

import io.github.pleuvoir.gateway.constants.PayTypeEnum;
import org.junit.Test;
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

	@Test
	public void test() {
		PaymentDTO dto = new PaymentDTO();
		dto.setType(PayTypeEnum.TYPE_WECHAT.getCode());
		ResultBasePayVO pay = agent.pay(dto);
		
		System.out.println(pay.toJSON());
	}
}
