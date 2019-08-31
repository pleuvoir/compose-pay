package io.github.pleuvoir.manager.common.authc;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.Destroyable;

import com.google.common.cache.CacheBuilder;

/**
 * shiro缓存Guava实现
 * @author abeir
 *
 */
public class GuavaShiroCache implements Cache<Object, Object>, Destroyable {
	
	private com.google.common.cache.Cache<Object,Object> cache;
	
	public GuavaShiroCache(long expireSeconds) {
		cache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(expireSeconds, TimeUnit.SECONDS).build();
	}
	
	
	@Override
	public void destroy() throws Exception {
		cache.invalidateAll();
	}


	@Override
	public Object get(Object key) throws CacheException {
		return cache.getIfPresent(key);
	}


	@Override
	public Object put(Object key, Object value) throws CacheException {
		cache.put(key, value);
		return value;
	}


	@Override
	public Object remove(Object key) throws CacheException {
		Object value = cache.getIfPresent(key);
		if(value!=null) {
			cache.invalidate(key);
		}
		return value;
	}


	@Override
	public void clear() throws CacheException {
		cache.invalidateAll();
	}


	@Override
	public int size() {
		long len = cache.size();
		return len>Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)len;
	}


	@Override
	public Set<Object> keys() {
		return cache.asMap().keySet();
	}


	@Override
	public Collection<Object> values() {
		return cache.asMap().values();
	}

}
