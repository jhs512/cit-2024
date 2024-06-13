package com.example.cit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableJpaAuditing
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CitApplication {

	public static void main(String[] args) {
		SpringApplication.run(CitApplication.class, args);
	}

}
