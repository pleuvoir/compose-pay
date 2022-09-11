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
package io.github.pleuvoir.gateway.service.internal.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.github.pleuvoir.gateway.dao.mer.MerSignFeeDao;
import io.github.pleuvoir.gateway.model.po.MerSignFeePO;
import io.github.pleuvoir.gateway.service.internal.MerSignFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商户签约支付产品
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Service
public class MerSignFeeServiceImpl implements MerSignFeeService {

    @Autowired
    private MerSignFeeDao merSignFeeDao;

    @Override
    public MerSignFeePO getByMidAndPayProductCode(String mid, String payProductCode) {
        MerSignFeePO query = new MerSignFeePO();
        query.setMid(mid);
        query.setPayProduct(payProductCode);
        return merSignFeeDao.selectOne(query);
    }

    @Override
    public MerSignFeePO getMerSignsByMidAndPayWayAndPayType(String mid, String payType, String payWay) {
        MerSignFeePO query = new MerSignFeePO();
        query.setMid(mid);
        query.setPayType(payType);
        query.setPayWay(payWay);
        return merSignFeeDao.selectOne(query);
    }

}
