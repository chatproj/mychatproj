package com.example.mychatproj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MychatprojApplication extends SpringBootServletInitializer{
	
	//ø‹¿Â ≈Ëƒπ war πË∆˜
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MychatprojApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(MychatprojApplication.class, args);
	}

}
