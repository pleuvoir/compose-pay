package io.github.pleuvoir.redpack.common.utils;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import org.apache.commons.lang3.StringUtils;

/**
 * 基于时间的UUID生成器
 *
 * <p>性能更高，可参考ACTIVITI工作流中的介绍
 * <a href="https://www.activiti.org/userguide/index.html?_ga=1.75398097.255837342.1464858165#advanced.uuid.generator">UUID id generator for high concurrency</a>
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class Generator {

    private static final String SEPARATOR = "-";

    private static final TimeBasedGenerator generator;

    static {
        generator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
    }

    public static String nextUUID() {
        return generator.generate().toString().replace(SEPARATOR, StringUtils.EMPTY);
    }
}
