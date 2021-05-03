package com.example.demo;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MyFirstTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyFirstTestApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPassworEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	
	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
		
	}
}
