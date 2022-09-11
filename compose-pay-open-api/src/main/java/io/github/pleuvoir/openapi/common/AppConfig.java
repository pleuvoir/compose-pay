package io.github.pleuvoir.openapi.common;

import lombok.Data;

/**
 * 系统配置
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Data
public class AppConfig {

    /**
     * 是否开启异步持久化数据
     */
    private boolean persist;

    /**
     * 崩溃恢复检测间隔，单位秒
     */
    private Integer crashRecoveryInteval;

}
