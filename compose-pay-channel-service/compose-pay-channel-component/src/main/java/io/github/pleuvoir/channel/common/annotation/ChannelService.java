package io.github.pleuvoir.channel.common.annotation;

import io.github.pleuvoir.pay.common.enums.ChannelEnum;
import io.github.pleuvoir.pay.common.enums.ServiceTypeEnum;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;

/**
 * 通道服务枚举
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ChannelService {

    /**
     * 通道
     */
    ChannelEnum channel();

    /**
     * 服务类别
     */
    ServiceTypeEnum serviceTypeEnum();

}
