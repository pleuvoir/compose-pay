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
package io.github.pleuvoir.gateway.model.vo;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author pleuvoir
 */
@Data
public class ResultBasePayVO implements Serializable {

    private static final long serialVersionUID = 5566661845252721918L;

    private String orderNo; // 商户系统中的订单号
    private BigDecimal amount; // 金额
    private String type; // 类型：wechat：微信 alipay：支付宝
    private String datetime; // 订单创建时间，yyyyMMddHHmmss
    private String qrCode; // 二维码链接

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
