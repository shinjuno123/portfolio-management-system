package com.amazing.juno.springwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringwebappApplication {
	


	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(SpringwebappApplication.class);

		app.run(args);
		
	
	}

}
