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

import com.amazing.juno.springwebapp.entity.About;

@Repository
public class AboutDaoImpl implements AboutDao{
	
    @Autowired
    private Environment env;
    
    @Value("${custom-property.basic-conetent}")
    private String basicContentSrc;
	

	@Override
	public About getAboutMe() {
		
		String description = env.getProperty("about.description");
		String period = env.getProperty("about.education.period");
		String school = env.getProperty("about.education.school");
		String degree = env.getProperty("about.education.degree");
		String regionCountry = env.getProperty("about.education.region&country");
		
		About about = new About(description, period, degree, school, regionCountry);

		return about;
	}


	@Override
	public void SetAboutMe(About about) {
		// Get Abstract superclass of Environment
		AbstractEnvironment absEnv = (AbstractEnvironment) env;
		
		// Read all keys
		Properties props = (Properties) absEnv.getPropertySources().get("content.props").getSource();
		

		props.setProperty("about.description", about.getDescription());
		props.setProperty("about.education.period", about.getPeriod());
		props.setProperty("about.education.school", about.getSchool());
		props.setProperty("about.education.degree", about.getDegree());
		props.setProperty("about.education.region&country", about.getRegionCountry());

		try {
			props.store(new FileOutputStream(basicContentSrc), null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
