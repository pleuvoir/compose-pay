package io.github.pleuvoir.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PayRiskLauncher {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(PayRiskLauncher.class);
		application.run(args);
	}

}