package io.github.pleuvoir.agent;

import io.github.pleuvoir.gateway.constants.PayTypeEnum;
import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.vo.ResultMessageVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.pleuvoir.BaseTest;
import io.github.pleuvoir.gateway.agent.BasePayAgent;
import io.github.pleuvoir.gateway.model.dto.PaymentDTO;
import io.github.pleuvoir.gateway.model.vo.ResultBasePayVO;

import java.math.BigDecimal;

/**
 * @author pleuvoir
 */
public class BasePayAgentTest extends BaseTest {


    @Autowired
    BasePayAgent agent;

    @Test
    public void test() {
        PaymentDTO dto = new PaymentDTO();
        dto.setType(PayTypeEnum.TYPE_WECHAT.getName());
        dto.setMid("88258920223");
        dto.setOrderNo(System.currentTimeMillis() + "");
        dto.setBody("body");
        dto.setIp("127.0.0.1");
        dto.setSubject("subject");
        dto.setAmount(BigDecimal.TEN);
        dto.setNotifyUrl("http://www.baidu.com");
        ResultMessageVO<ResultBasePayVO> pay = agent.payCode(dto);
        if (pay.isSuccess()) {
            ResultBasePayVO data = pay.getData();
            System.out.println(data);
        }else{
            System.out.println("验证结果=-=-=-=-=-=-=-=-=-=-=-=-=" + pay.toJSON());
        }
    }
}
