package com.pedroluizforlan.pontodoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PontodocApplication {

	public static void main(String[] args) {
		SpringApplication.run(PontodocApplication.class, args);
	}

}
