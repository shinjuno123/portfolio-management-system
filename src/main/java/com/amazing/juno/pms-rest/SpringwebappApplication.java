package com.amazing.juno.springwebapp;

import com.amazing.juno.springwebapp.properties.FileUploadProperties;
import com.amazing.juno.springwebapp.properties.JWTConstraints;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Locale;


@Slf4j
@SpringBootApplication
@EnableConfigurationProperties({
		FileUploadProperties.class,
		JWTConstraints.class
})
public class SpringwebappApplication extends SpringBootServletInitializer {



	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringwebappApplication.class);
	}

	public static void main(String[] args) {

		Locale.setDefault(Locale.ENGLISH);
		SpringApplication app = new SpringApplication(SpringwebappApplication.class);

		app.run(args);
		
	
	}

}
