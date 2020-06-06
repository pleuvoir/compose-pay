package io.github.pleuvoir.gateway.common.aop;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodTimeLog {

    /**
     * 业务名称
     */
    String value() default StringUtils.EMPTY;
}
