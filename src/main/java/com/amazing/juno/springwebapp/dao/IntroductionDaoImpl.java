package com.amazing.juno.springwebapp.dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.amazing.juno.springwebapp.entity.Introduction;


@Repository
public class IntroductionDaoImpl implements IntroductionDao{

    @Autowired
    private Environment env;
    
    @Value("${custom-property.basic-conetent}")
    private String basicContentSrc;
    
    
	public IntroductionDaoImpl() {
	}


	@Override
	public Introduction getIntro() {
		
		String main = env.getProperty("introduction.title.main");
		String name = env.getProperty("introduction.title.name");
		String sub = env.getProperty("introduction.title.sub");
		String detail = env.getProperty("introduction.title.detail");

		
		return new Introduction();
	}


	@Override
	public void setIntro(Introduction intro) {
		// Get Abstract superclass of Environment

	}



}
