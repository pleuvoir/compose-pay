package io.github.pleuvoir.openapi.redis;

import io.github.pleuvoir.openapi.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
public class RedisTest extends BaseTest {

    @Autowired
    private RedisLockRegistry redisLockRegistry;


    @Test
    public void lock() throws InterruptedException {
        Lock lock = redisLockRegistry.obtain("lock");
        boolean b1 = lock.tryLock(3, TimeUnit.SECONDS);
        log.info("b1 is : {}", b1);
        boolean b2 = lock.tryLock(3, TimeUnit.SECONDS);
        log.info("b2 is : {}", b2);
        TimeUnit.SECONDS.sleep(5);

//        boolean b2 = lock.tryLock(3, TimeUnit.SECONDS);
//        log.info("b2 is : {}", b2);
//
        lock.unlock();
        lock.unlock();
    }
}
