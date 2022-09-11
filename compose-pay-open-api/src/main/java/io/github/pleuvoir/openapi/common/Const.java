package io.github.pleuvoir.openapi.common;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class Const {

    public static final int OPTIMISTIC_LOCK_RETRY_MAX = 3;


    /**
     * redis红包队列前缀
     */
    public static final String REDIS_QUEUE_NAME = "Redpack:redpackQueues:";


    /**
     * 抢到红包后异步持久化队列
     */
    public static final String REDIS_PERSIST_QUEUE_NAME = "Redpack:persistQueues:";

    /**
     * 抢到红包后异步持久化备份队列
     */
    public static final String REDIS_PERSIST_QUEUE_NAME_BAK = "Redpack:persistQueues:bak:";
}
