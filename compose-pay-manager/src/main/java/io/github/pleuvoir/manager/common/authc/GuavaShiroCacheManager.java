package io.github.pleuvoir.manager.common.authc;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

/**
 * shiro缓存管理器的Guava实现
 * @author abeir
 *
 */
public class GuavaShiroCacheManager extends AbstractCacheManager{
	//默认失效时间
	private static long EXPIRE_SECONDS = 30;
	
	@Override
	protected Cache<?,?> createCache(String name) throws CacheException {
		return new GuavaShiroCache(EXPIRE_SECONDS);
	}
	
}
