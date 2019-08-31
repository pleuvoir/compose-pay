package io.github.pleuvoir.manager.common.authc;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.Destroyable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * shiro缓存redis实现
 * @author abeir
 *
 */
public class RedisShiroCache implements Cache<Object, Object>, Destroyable {
	
	private RedisTemplate<String,Object> redisTemplate;
	
	private String bigKey;
	private long expireSeconds = 60;
	private HashOperations<String,Object,Object> hash;
	
	public RedisShiroCache(String bigKey, long expireSeconds, RedisTemplate<String,Object> redisTemplate) {
		this.bigKey = bigKey;
		this.expireSeconds = expireSeconds;
		this.redisTemplate = redisTemplate;
		this.hash = redisTemplate.opsForHash();
	}
	
	
	@Override
	public void destroy() throws Exception {
		if(StringUtils.isNotBlank(bigKey)) {
			redisTemplate.delete(bigKey);
		}
		this.redisTemplate = null;
	}


	@Override
	public Object get(Object hkey) throws CacheException {
		return hash.get(bigKey, hkey);
	}


	@Override
	public Object put(Object hkey, Object value) throws CacheException {
		hash.put(bigKey, hkey, value);
		redisTemplate.expire(bigKey, expireSeconds, TimeUnit.SECONDS);
		return value;
	}


	@Override
	public Object remove(Object hkey) throws CacheException {
		Object value = hash.get(bigKey, hkey);
		if(value!=null) {
			hash.delete(bigKey, hkey);
		}
		return value;
	}


	@Override
	public void clear() throws CacheException {
		redisTemplate.delete(bigKey);
	}


	@Override
	public int size() {
		Long len = hash.size(bigKey);
		if(len!=null) {
			return len.intValue();
		}
		return 0;
	}


	@Override
	public Set<Object> keys() {
		return hash.keys(bigKey);
	}


	@Override
	public Collection<Object> values() {
		return (Collection<Object>) hash.values(bigKey);
	}

}
