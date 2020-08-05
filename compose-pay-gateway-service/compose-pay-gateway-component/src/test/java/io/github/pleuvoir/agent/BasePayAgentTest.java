/*
 * Copyright © 2020 pleuvoir (pleuvior@foxmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.pleuvoir.agent;

import io.github.pleuvoir.channel.model.request.PaymentDTO;
import io.github.pleuvoir.gateway.constants.PayTypeEnum;
import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.vo.ResultMessageVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.pleuvoir.BaseTest;
import io.github.pleuvoir.gateway.agent.BasePayAgent;
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
//        PaymentDTO dto = new PaymentDTO();
//        dto.setType(PayTypeEnum.TYPE_WECHAT.getName());
//        dto.setMid("88258920223");
//        dto.setOrderNo(System.currentTimeMillis() + "");
//        dto.setBody("body");
//        dto.setIp("127.0.0.1");
//        dto.setSubject("subject");
//        dto.setAmount(BigDecimal.TEN);
//        dto.setNotifyUrl("http://www.baidu.com");
//        ResultMessageVO<ResultBasePayVO> pay = agent.payCode(dto);
//        if (pay.isSuccess()) {
//            ResultBasePayVO data = pay.getData();
//            System.out.println(data);
//        }else{
//            System.out.println("验证结果=-=-=-=-=-=-=-=-=-=-=-=-=" + pay.toJSON());
//        }
    }
}
