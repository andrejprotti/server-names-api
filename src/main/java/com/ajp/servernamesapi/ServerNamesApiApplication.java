package com.ajp.servernamesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ServerNamesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerNamesApiApplication.class, args);
	}
}