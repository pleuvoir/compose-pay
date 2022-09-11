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

import io.github.pleuvoir.gateway.model.po.MerChannelPO;

import java.util.List;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public interface MerChannelService {

    /**
     * 获取可用的商户通道关系
     *
     * @param mid     商户号
     * @param payType 支付种类
     * @param payWay  支付方式
     */
    List<MerChannelPO> getUsableMerCnlList(String mid, String payType, String payWay);

    /**
     * 最高优先级的商户通道关系
     *
     * @param mid     商户号
     * @param payType 支付种类
     * @param payWay  支付方式
     */
    MerChannelPO maxPriority(String mid, String payType, String payWay);
}
