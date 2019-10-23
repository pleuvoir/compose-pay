package io.github.pleuvoir.gateway.common;

public final class Const {

    /**
     * 缓存注解@Cacheable的key参数通用表达式
     */
    public static final String CACHEABLE_KEY_EXPRESSION = "#root.targetClass.getSimpleName() + ':' + #root.methodName";

	public static final String CACHE_KEY_EXPRESSION_MERCHANT = CACHEABLE_KEY_EXPRESSION + "+ ':' + #getMerchant";

    /**
     * 缓存统一前缀
     */
    public static final String CACHE_PREFIX = "ComposePayGateway:";

    /**
     * 锁过期时长（秒）
     */
    public static final int LOCK_EXPIRE_SECONDS = 5;

}
