package io.github.pleuvoir.channel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 通道服务启动
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@SpringBootApplication
public class PayChannelLauncher {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(PayChannelLauncher.class);
        application.run(args);
    }

}
