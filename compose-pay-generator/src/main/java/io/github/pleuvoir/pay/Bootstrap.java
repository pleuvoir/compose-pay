package io.github.pleuvoir.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = { "classpath:application-context.xml" })
@Import(Generator.class)
public class Bootstrap implements CommandLineRunner {

	@Autowired
	private Generator generator;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Bootstrap.class);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		generator.CRUD("p_pay_way");
	}
}
