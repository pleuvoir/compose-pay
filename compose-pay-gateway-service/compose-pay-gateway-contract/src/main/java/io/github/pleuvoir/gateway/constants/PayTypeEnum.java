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
import org.apache.commons.lang3.StringUtils;

/**
 * 支付种类
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public enum PayTypeEnum {

    /**
     * 支付宝支付
     */
    TYPE_ALIPAY("01", "wechat"),

    /**
     * 微信支付
     */
    TYPE_WECHAT("02", "alipay"),

    ;

    @Getter
    private String code;    //编号，数据库中存储的值

    @Getter
    private String name;    //支付种类名称

    PayTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }


    /**
     * 获取支付种类
     *
     * @param name 支付种类名称
     */
    public static PayTypeEnum toEumByName(String name) {
        PayTypeEnum[] payTypeEnums = PayTypeEnum.values();
        for (PayTypeEnum value : payTypeEnums) {
            if (StringUtils.equalsIgnoreCase(value.getName(), name)) {
                return value;
            }
        }
        return null;
    }

}
