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
package io.github.pleuvoir.pay.common.utils;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * 最大努力重试拒绝策略
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
public class RetryRejectedExecutionHandler implements RejectedExecutionHandler {

    private static final long DEFAULT_TIMEOUT_SECOND = 60;

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        try {
            log.warn("线程池触发拒绝策略 ：）尝试重新加入处理队列中，task-name={}，当前 queue-size={}",
                    r.getClass().getSimpleName(),
                    executor.getQueue().size());

            executor.getQueue().offer(r, DEFAULT_TIMEOUT_SECOND, TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            log.error("{} 秒还是未成功放进线程池队列中丢弃处理，task-name={}，当前 queue-size={}",
                    DEFAULT_TIMEOUT_SECOND,
                    r.getClass().getSimpleName(),
                    executor.getQueue().size(),
                    e);
        }
    }
}
