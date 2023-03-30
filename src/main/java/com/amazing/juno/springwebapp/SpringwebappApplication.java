package com.amazing.juno.springwebapp;

import com.amazing.juno.springwebapp.properties.FileUploadProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
@EnableConfigurationProperties({
		FileUploadProperties.class
})
public class SpringwebappApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringwebappApplication.class);
	}

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(SpringwebappApplication.class);

		app.run(args);
		
	
	}

}
