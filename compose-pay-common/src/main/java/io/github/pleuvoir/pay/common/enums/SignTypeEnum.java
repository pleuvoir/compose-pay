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

import org.apache.commons.lang3.StringUtils;

/**
 * 对外接口的签名类型
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public enum SignTypeEnum {
    /**
     * MD5签名方式
     */
    MD5("md5");

    private String type;

    private SignTypeEnum(String type) {
        this.type = type;
    }

    /**
     * 通过字符串签名类型的获取枚举
     *
     * @return 若不是有效的签名类型返回null
     */
    public static SignTypeEnum toEnum(String type) {
        if (StringUtils.isBlank(type)) {
            return null;
        }
        type = type.trim();
        SignTypeEnum[] types = SignTypeEnum.values();
        for (SignTypeEnum t : types) {
            if (type.equalsIgnoreCase(t.toString())) {
                return t;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return type;
    }
}
