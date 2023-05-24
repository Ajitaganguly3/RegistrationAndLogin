package com.moviebookingapp.registrationAndLogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class RegistrationAndLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistrationAndLoginApplication.class, args);
	}

}
