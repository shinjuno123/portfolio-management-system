package com.amazing.juno.springwebapp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.amazing.juno.springwebapp.entity.AboutEntity;

@Repository
public class AboutDao implements AboutInterface{
	
    @Autowired
    private Environment env;
	

	@Override
	public AboutEntity getAboutMe() {
		
		String description = env.getProperty("about.description");
		String period = env.getProperty("about.education.period");
		String school = env.getProperty("about.education.school");
		String degree = env.getProperty("about.education.degree");
		String regionCountry = env.getProperty("about.education.region&country");
		
		AboutEntity about = new AboutEntity(description, period, degree, school, regionCountry);

		return about;
	}

}
