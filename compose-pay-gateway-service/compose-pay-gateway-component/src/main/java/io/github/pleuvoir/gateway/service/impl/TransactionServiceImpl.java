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
package io.github.pleuvoir.gateway.service.impl;

import io.github.pleuvoir.gateway.model.po.MerPayPO;
import io.github.pleuvoir.gateway.model.po.MerchantPO;
import io.github.pleuvoir.gateway.service.ITransactionService;
import io.github.pleuvoir.gateway.service.internal.IMerPayService;
import io.github.pleuvoir.gateway.utils.AssertUtil;
import io.github.pleuvoir.pay.common.exception.BusinessException;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
@Service
public class TransactionServiceImpl implements ITransactionService {

    @Resource
    private IMerPayService merPayService;


    @Override
    public MerPayPO createOrder(MerPayPO order, MerchantPO merchant) throws BusinessException {

        Integer ret = merPayService.save(order);
        AssertUtil.assertOne(ret,"创建支付订单失败");
        return null;
    }

    @Override
    public MerPayPO queryOrderByES() {
        return null;
    }
}
