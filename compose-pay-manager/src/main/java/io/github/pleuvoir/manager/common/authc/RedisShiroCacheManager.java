package io.github.pleuvoir.manager.common.authc;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.springframework.data.redis.core.RedisTemplate;

import io.github.pleuvoir.manager.common.Const;

import javax.annotation.Resource;

/**
 * shiro缓存管理器的redis实现
 * @author abeir
 *
 */
public class RedisShiroCacheManager implements CacheManager, Destroyable{
	
	@Resource(name="jdkRedisTemplate")
	private RedisTemplate<String,Object> redisTemplate;
	
	@Override
	public void destroy() throws Exception {
	}

	@Override
	@SuppressWarnings("unchecked")
	public Cache<?, ?> getCache(String name) throws CacheException {
		if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Cache name cannot be null or empty.");
        }
		String key = new StringBuilder(Const.REDIS_CACHE_PREFIX).append("shiro:").append(name).toString();
		return new RedisShiroCache(key, 60, redisTemplate);
	}


}
