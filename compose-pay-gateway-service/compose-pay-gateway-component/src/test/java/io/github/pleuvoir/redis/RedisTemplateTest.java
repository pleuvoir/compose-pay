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
