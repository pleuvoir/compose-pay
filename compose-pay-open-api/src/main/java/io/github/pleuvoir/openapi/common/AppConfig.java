package io.github.pleuvoir.redpack.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统配置
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
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
