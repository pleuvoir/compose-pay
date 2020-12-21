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
package io.github.pleuvoir.pay.common.enums;

import lombok.Getter;

/**
 * 业务结果码
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public enum ResultCodeEnum {


    //0-100 公共
    SUCCESS(0, "成功"),
    FAIL(1, "操作失败"),
    ERROR(2, "系统繁忙，请稍后再试。"),
    VERIFY_FAILED(3, "验签失败"),
    LACK_PARAM(4, "缺少参数"),
    PARAM_ERROR(5, "参数错误"),
    INVALID_PAY_TYPE(6, "无效的支付方式"),
    CHANNEL_SERVICE_EXCEPTION(100, "通道服务异常"),


    //101-200 商户
    NO_MERCHANT(101, "商户不存在"),
    INVALID_MERCHANT(102, "无效商户"),
    IP_NO_BIND(103, "商户IP未绑定"),
    MER_UN_SIGN_ERROR(104, "商户未签约该支付种类和支付方式"),
    NOT_FOUND_CHANNEL_MID(105, "未获取到通道商户号"),


    //201-300 支付
    TRADE_ALREADY_EXIST(201, "支付订单已存在"),





    ;

    @Getter
    private Integer code;

    @Getter
    private String msg;

    private ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 若参数是字符串，判断字符串是否与code相同<br> 若参数是ResultCode对象，判断其中的code是否相同
     */
    public boolean isEquals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            return this.getCode().equals(obj);
        } else if (obj instanceof ResultCodeEnum) {
            return this.getCode().equals(((ResultCodeEnum) obj).getCode());
        }
        return false;
    }

}
