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

import io.github.pleuvoir.gateway.model.po.MerchantPO;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public interface MerchantService {

    /**
     * 根据mid获取商户信息，不存在时返回null
     * @param mid 商户号
     */
    MerchantPO getByMid(String mid);
}
