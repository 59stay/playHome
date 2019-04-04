package com.jyb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
@ServletComponentScan
@SpringBootApplication
public class PlayHouseApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PlayHouseApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(PlayHouseApplication.class, args);
	}

}
