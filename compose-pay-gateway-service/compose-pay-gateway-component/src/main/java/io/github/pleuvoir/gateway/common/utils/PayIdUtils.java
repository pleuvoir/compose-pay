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
package io.github.pleuvoir.gateway.common.utils;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 雪花算法与多键分表
 *
 * <p>
 * 可参考  https://juejin.cn/post/6844904200346796046
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
public class PayIdUtils {

    /**
     * 支付流水号作为水平分表键。 其中transUniqueId hash后截取末7位作为分表基因。最大支持128分表
     */
    public static long getPayId(Long transUniqueId) {
        try {
            //获取64位
            long preID = IdUtils.nextId();
            //清空低7位为0，如果自己生成57位，可跳过此步骤
            preID = (preID >> 7) << 7;
            //hash为了保证传入transUniqueId的均匀。
            long hashcode = hash(String.valueOf(transUniqueId));
            //57位拼上hash后的低7位
            return preID | (hashcode & 127);
        } catch (Exception e) {
            log.error("生成支付流水号失败", e);
        }
        return 0;
    }

    private static long hash(String key) {
        byte[] keys = key.getBytes(StandardCharsets.UTF_8);
        int hashcode = new HashCodeBuilder().append(keys).toHashCode();
        return hashcode & 0x7FFFFFFF;
    }

}
