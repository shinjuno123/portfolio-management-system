package com.amazing.juno.springwebapp.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.amazing.juno.springwebapp.dto.TechnologyListDto;
import com.amazing.juno.springwebapp.entity.IntroductionEntity;


@Repository
public class IntroductionDao implements IntroductionDaoInterface{

    @Autowired
    private Environment env;
    
    
	public IntroductionDao() {
	}


	@Override
	public IntroductionEntity getIntro() {
		
		String main = env.getProperty("introduction.title.main");
		String name = env.getProperty("introduction.title.name");
		String sub = env.getProperty("introduction.title.sub");
		String detail = env.getProperty("introduction.title.detail");
		
		IntroductionEntity intro = new IntroductionEntity(main, name, sub, detail);
		
		return intro;
	}


	@Override
	public void setIntro(IntroductionEntity intro) {
		// Get Abstract superclass of Environment
		AbstractEnvironment absEnv = (AbstractEnvironment) env;
		List<TechnologyListDto> stack = new LinkedList<>();
		
		// Read all keys
		System.out.println(env.getProperty("server.port"));
		
//		props.setProperty("introduction.title.main", intro.getTitleMain());
//		props.setProperty("introduction.title.name", intro.getMyName());
//		props.setProperty("introduction.title.sub",intro.getSubTitle());
//		props.setProperty("introduction.title.detail", intro.getDetail());
//		
//		try {
//			props.store(new FileOutputStream("web-basic-content.properties"), null);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}



}
