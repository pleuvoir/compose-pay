package io.github.pleuvoir.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PayGatewayLauncher {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(PayGatewayLauncher.class);
		application.run(args);
	}

}