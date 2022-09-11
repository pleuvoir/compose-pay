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
package io.github.pleuvoir.gateway.service.internal;

import io.github.pleuvoir.gateway.model.po.MerSignFeePO;
import io.github.pleuvoir.gateway.model.po.MerchantPO;

import java.util.List;

/**
 * 基础服务
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public interface BaseService {

    /**
     * 获取商户信息
     *
     * @param mid 商户号
     */
    MerchantPO getMerchant(String mid);

    /**
     * 获取商户签约的支付产品
     *
     * @param mid            商户号
     * @param payProductCode 支付产品编号
     */
    MerSignFeePO getMerSignsByMidAndPayProductCode(String mid, String payProductCode);

    /**
     * 获取商户签约的支付产品
     *
     * @param mid     商户号
     * @param payType 支付种类编号
     * @param payWay  支付方式编号
     */
    MerSignFeePO getMerSignsByMidAndPayWayAndPayType(String mid, String payType, String payWay);
}
