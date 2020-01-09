package com.sendtomoon.credit;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Startup {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Startup.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}
}
