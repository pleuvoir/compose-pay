package io.github.pleuvoir.redpack.redis;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import io.github.pleuvoir.redpack.BaseTest;
import io.github.pleuvoir.redpack.common.Const;
import io.github.pleuvoir.redpack.model.po.RedpackPO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
public class RedisTest extends BaseTest {

    @Autowired
    private RedisTemplate<String, RedpackPO> redisTemplate;
    @Autowired
    private RedisLockRegistry redisLockRegistry;

    @Test
    public void test(){
        List<RedpackPO> queue = Lists.newArrayList();
        for (Integer i = 0; i < 5; i++) {
            RedpackPO redpackPO = new RedpackPO();
            redpackPO.setId(System.currentTimeMillis());
            redpackPO.setAmount(BigDecimal.ONE);
            redpackPO.setStatus(RedpackPO.ENABLE);
            redpackPO.setActivityId(1L);
            queue.add(redpackPO);
        }

        //加入redis
        String queueKey = Const.REDIS_QUEUE_NAME.concat("1");

        System.out.println(JSON.toJSONString(queue));
        redisTemplate.opsForList().rightPushAll(queueKey, queue);
        RedpackPO redpackPO = redisTemplate.opsForList().leftPop(queueKey);
        System.out.println(redpackPO);

    }


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
