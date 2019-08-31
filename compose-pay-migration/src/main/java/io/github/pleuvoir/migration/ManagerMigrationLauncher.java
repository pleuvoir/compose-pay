package io.github.pleuvoir.migration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ManagerMigrationLauncher {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ManagerMigrationLauncher.class);
		application.run(args);
	}
}
