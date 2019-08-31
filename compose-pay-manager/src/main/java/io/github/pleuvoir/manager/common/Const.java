package io.github.pleuvoir.manager.common;

public final class Const {
	/** 控制是否记录操作日志，设置为true表示需要记录日志 */
	public static final boolean RECORD_OPERATION_LOG = true;

	public static final String REDIS_CACHE_PREFIX = "ManagerTemplate:";
	
	public static final String PASSWORD_ALGORITHM_NAME = "md5";
	
	public static final int PASSWORD_HASH_ITERATIONS = 2;
	
	public static final String SESSION_USER = "__session_user";
	
	/** 缓存注解@Cacheable的key参数通用表达式 */
	public static final String CACHEABLE_KEY_EXPRESSION = "#root.targetClass.getSimpleName() + ':' + #root.methodName";
	/** 缓存注解@Cacheable的value参数通用表达式 */
	public static final String CACHEABLE_VALUE = "0";
	
	/** 默认的分页行数 */
	public static final int DEFAULT_PAGE_ROWS = 20;

	public static final String SESSION_KAPTCHA = "__session_kaptcha";

}
