package com.amazing.juno.springwebapp.dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

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
		
		Introduction intro = new Introduction(main, name, sub, detail);
		
		return intro;
	}


	@Override
	public void setIntro(Introduction intro) {
		// Get Abstract superclass of Environment
		AbstractEnvironment absEnv = (AbstractEnvironment) env;
		
		// Read all keys
		Properties props = (Properties) absEnv.getPropertySources().get("content.props").getSource();
		

		props.setProperty("introduction.title.main", intro.getTitleMain());
		props.setProperty("introduction.title.name", intro.getMyName());
		props.setProperty("introduction.title.sub",intro.getSubTitle());
		props.setProperty("introduction.title.detail", intro.getDetail());

		try {
			props.store(new FileOutputStream(basicContentSrc), null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



}
