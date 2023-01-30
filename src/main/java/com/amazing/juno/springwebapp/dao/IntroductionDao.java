package com.amazing.juno.springwebapp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.amazing.juno.springwebapp.entity.IntroductionEntity;


@Repository
public class IntroductionDao implements IntroductionDaoInterface{

    @Autowired
    private Environment env;
    


	@Override
	public IntroductionEntity getIntro() {
		
		String main = env.getProperty("introduction.title.main");
		String name = env.getProperty("introduction.title.name");
		String sub = env.getProperty("introduction.title.sub");
		String detail = env.getProperty("introduction.title.detail");
		
		IntroductionEntity intro = new IntroductionEntity(main, name, sub, detail);
		
		return intro;
	}



}
