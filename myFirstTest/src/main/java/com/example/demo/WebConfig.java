package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	public  void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/users")
		.allowedMethods("GET", "POST","PUT")
		.allowedOrigins("http://localhost:4200");
	}


}
