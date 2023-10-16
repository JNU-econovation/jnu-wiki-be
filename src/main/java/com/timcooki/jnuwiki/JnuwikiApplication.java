package com.timcooki.jnuwiki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JnuwikiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JnuwikiApplication.class, args);
	}

}
