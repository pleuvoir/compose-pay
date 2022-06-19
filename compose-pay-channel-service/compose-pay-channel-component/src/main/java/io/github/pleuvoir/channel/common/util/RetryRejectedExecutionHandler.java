package io.github.pleuvoir.channel.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 最大努力重试拒绝策略
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
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
