package io.github.pleuvoir.channel;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Configuration
@EnableDubbo(scanBasePackages = {"io.github.pleuvoir.channel"})
@ImportResource(locations = {"classpath:config/app-core.xml"})
public class PayChannelConfiguration {
}
