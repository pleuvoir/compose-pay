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
package io.github.pleuvoir.gateway.constants;

import lombok.Getter;

/**
 * 业务结果码
 *
 * @author pleuvoir
 */
public enum ResultCodeEnum {

    /**
     * 成功
     */
    SUCCESS("SUCCESS", "ok"),
    /**
     * 操作失败
     */
    FAIL("FAIL", "操作失败"),
    /**
     * 系统错误
     */
    ERROR("ERROR", "系统繁忙，请稍后再试。"),
    /**
     * 验签失败
     */
    VERIFY_FAILED("VERIFY_FAILED", "验签失败"),
    /**
     * 缺少参数
     */
    LACK_PARAM("LACK_PARAM", "缺少参数"),
    /**
     * 商户不存在
     */
    NO_MERCHANT("NO_MERCHANT", "商户不存在"),
    /**
     * 无效商户
     */
    INVALID_MERCHANT("INVALID_MERCHANT", "无效商户"),
    /**
     * 商户IP未绑定
     */
    IP_NO_BIND("IP_NO_BIND", "商户IP未绑定"),
    /**
     * 参数错误
     */
    PARAM_ERROR("PARAM_ERROR", "参数错误"),
    /**
     * 无效的支付种类
     */
    INVALID_PAY_TYPE("INVALID_PAY_TYPE", "无效的支付方式"),
    /**
     * 商户未签约该支付种类和支付方式
     */
    MER_UN_SIGN_ERROR("MER_UN_SIGN_ERROR", "商户未签约该支付种类和支付方式"),
    /**
     * 未获取到通道商户号
     */
    NOT_FOUND_CHANNEL_MID("NOT_FOUND_CHANNEL_MID", "未获取到通道商户号"),

    ;

    @Getter
    private String code;

    @Getter
    private String msg;

    private ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 若参数是字符串，判断字符串是否与code相同<br>
     * 若参数是ResultCode对象，判断其中的code是否相同
     *
     * @param obj
     * @return
     */
    public boolean isEquals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            return this.getCode().equals((String) obj);
        } else if (obj instanceof ResultCodeEnum) {
            return this.getCode().equals(((ResultCodeEnum) obj).getCode());
        }
        return false;
    }

}
