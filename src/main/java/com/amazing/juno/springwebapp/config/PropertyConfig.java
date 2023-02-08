package com.amazing.juno.springwebapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value="classpath:custom-properties/web-basic-content.properties", name="content.props")
public class PropertyConfig {
	

	
}
