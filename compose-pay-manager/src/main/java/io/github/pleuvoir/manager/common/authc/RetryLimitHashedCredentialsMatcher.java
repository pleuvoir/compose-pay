package io.github.pleuvoir.manager.common.authc;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import io.github.pleuvoir.manager.common.Const;
import io.github.pleuvoir.manager.service.pub.PubParamService;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
	
	private static final String PREFIX = Const.REDIS_CACHE_PREFIX + "Login:";
	
	@Resource(name="stringRedisTemplate")
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private PubParamService paramService;
	
	public RetryLimitHashedCredentialsMatcher() {
		super();
		setHashIterations(Const.PASSWORD_HASH_ITERATIONS);
		setHashAlgorithmName(Const.PASSWORD_ALGORITHM_NAME);
	}
	
	
	private String getKey(String username) {
		return PREFIX.concat(username);
	}
	
	private int getFailCount(String username) {
		String value = redisTemplate.opsForValue().get(getKey(username));
		if(StringUtils.isBlank(value)) {
			return 0;
		}
		return Integer.parseInt(value);
	}
	
	private int incrementFailCount(String username) {
		String key = getKey(username);
		Long times = redisTemplate.opsForValue().increment(key, 1);
		redisTemplate.expire(key, paramService.getLoginLockTime(), TimeUnit.SECONDS);
		if(times==null) {
			return 0;
		}
		return times.intValue();
	}
	
	private void cleanFailCount(String username) {
		redisTemplate.delete(getKey(username));
	}
	
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		
		String username = (String) token.getPrincipal();
		int failCount = getFailCount(username);
		if(failCount > paramService.getLoginErrorCount()) {
			throw new ExcessiveAttemptsException("超过最大尝试次数");
		}
		
		boolean match = super.doCredentialsMatch(token, info);
		if(match) {
			cleanFailCount(username);
		}else {
			incrementFailCount(username);
		}
		return match;
	}

	@Override
	public String getHashAlgorithmName() {
		return Const.PASSWORD_ALGORITHM_NAME;
	}
	
	@Override
	public int getHashIterations() {
		return Const.PASSWORD_HASH_ITERATIONS;
	}
	
}
