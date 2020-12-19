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
package io.github.pleuvoir.gateway.common;

public final class Const {

    /**
     * 缓存注解@Cacheable的key参数通用表达式
     */
    public static final String CACHEABLE_KEY_EXPRESSION = "#root.targetClass.getSimpleName() + ':' + #root.methodName";

	public static final String CACHE_KEY_EXPRESSION_MERCHANT = CACHEABLE_KEY_EXPRESSION + "+ ':' + #getMerchant";

    /**
     * 缓存统一前缀
     */
    public static final String CACHE_PREFIX = "ComposePayGateway:";

    /**
     * 锁过期时长（秒）
     */
    public static final int LOCK_EXPIRE_SECONDS = 5;

}
