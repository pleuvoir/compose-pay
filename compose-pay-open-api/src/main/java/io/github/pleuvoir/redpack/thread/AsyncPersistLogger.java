package io.github.pleuvoir.redpack.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 记录异步持久化日志，错误恢复使用
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */

public class AsyncPersistLogger {

    public static final Logger LOGGER = LoggerFactory.getLogger("asyncPersistLogger");


    /**
     * 打印日志
     */
    public static void save(String opt, String cmdName, String cmdJSON) {
        LOGGER.info("[{}]{}={}/EOL/", opt, cmdName, cmdJSON);
    }
}
