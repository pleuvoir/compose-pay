/*
 * Copyright © 2020 pleuvoir (pleuvoir@foxmail.com)
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
package io.github.pleuvoir.redis;

import io.github.pleuvoir.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class RedisTemplateTest extends BaseTest {


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void test(){

        redisTemplate.opsForValue().set("name","pleuvoir",1, TimeUnit.MINUTES);
    }
}
