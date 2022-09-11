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
package io.github.pleuvoir.gateway.agent;

import io.github.pleuvoir.gateway.model.dto.CloseOrderDTO;
import io.github.pleuvoir.gateway.model.dto.CloseOrderResultDTO;
import io.github.pleuvoir.gateway.model.dto.PayQueryDTO;
import io.github.pleuvoir.gateway.model.dto.PayQueryResultDTO;
import io.github.pleuvoir.gateway.model.dto.PayRequestDTO;
import io.github.pleuvoir.gateway.model.dto.PayRequestResultDTO;
import io.github.pleuvoir.gateway.model.dto.RefundApplyDTO;
import io.github.pleuvoir.gateway.model.dto.RefundApplyResultDTO;
import io.github.pleuvoir.gateway.model.dto.RefundQueryDTO;
import io.github.pleuvoir.gateway.model.dto.RefundQueryResultDTO;
import io.github.pleuvoir.pay.common.model.Result;
import java.util.List;

/**
 * 对外支付接口，提供：
 * <p>
 * 预支付 支付结果查询 退款 退款结果查询 订单关闭
 * </p>
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public interface IPayAgent {

    /**
     * 预支付接口
     */
    Result<PayRequestResultDTO> pay(PayRequestDTO payRequestDTO);

    /**
     * 支付结果查询
     */
    Result<PayQueryResultDTO> payQuery(PayQueryDTO payQueryDTO);

    /**
     * 退款申请
     */
    Result<RefundApplyResultDTO> refundApply(RefundApplyDTO refundApplyDTO);

    /**
     * 退款结果查询
     */
    Result<List<RefundQueryResultDTO>> refundQuery(RefundQueryDTO refundQueryDTO);

    /**
     * 关闭订单
     */
    Result<CloseOrderResultDTO> closeOrder(CloseOrderDTO closeOrderDTO);
}
