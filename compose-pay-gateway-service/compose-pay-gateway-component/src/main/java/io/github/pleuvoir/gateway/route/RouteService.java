/*
 * Copyright © 2020 pleuvoir (pleuvoir@foxmail.com)
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
package io.github.pleuvoir.gateway.route;

import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.po.MerChannelPO;

/**
 * 路由服务
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface RouteService {

    /**
     * 找到适合商户的渠道
     *
     * @param mid     商户号
     * @param payType 支付种类编码
     * @param payWay  支付方式编码
     */
    MerChannelPO find(String mid, String payType, String payWay) throws BusinessException;
}
