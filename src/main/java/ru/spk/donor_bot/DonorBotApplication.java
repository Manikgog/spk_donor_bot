package ru.spk.donor_bot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class DonorBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(DonorBotApplication.class, args);
	}

}
