package com.amazing.juno.springwebapp.dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.amazing.juno.springwebapp.dto.TechnologyCategory;
import com.amazing.juno.springwebapp.entity.Technology;

@Repository
public class TechnologyDaoImpl implements TechnologyDao {
	
	@Autowired
	private Environment env;
	
    @Value("${custom-property.basic-conetent}")
    private String basicContentSrc;
	

	public TechnologyDaoImpl() {
		
	}
	
	private String stringifyTechList(List<Technology> techList) {
		StringBuilder result = new StringBuilder();
		for(Technology tech :techList) {
			result.append(tech.getSkill());
			result.append("/");
			result.append(tech.getScore());
			result.append(",");
		}
		
		return result.toString().substring(0, result.length() - 1);
	}
	
	
	private List<Technology> extractTechList(String key){
		String[] skills = env.getProperty(key, String[].class);
		List<Technology> skillList = new ArrayList<>();
		
		// Fill techList
		for(int i=0; i< skills.length;i++) {
			String[] techDetail = skills[i].split("/");
			skillList.add(new Technology(Integer.parseInt(techDetail[1]),techDetail[0]));
		}
		
		return skillList;
	}

	@Override
	public List<TechnologyCategory> getTechnologyStack() {
		// Get Abstract superclass of Environment
		AbstractEnvironment absEnv = (AbstractEnvironment) env;
		List<TechnologyCategory> stack = new LinkedList<>();
		
		// Read all keys
		Properties props = (Properties) absEnv.getPropertySources().get("content.props").getSource();
		for(String key: props.stringPropertyNames()) {
			// Extract a string starting with tech
			if(key.startsWith("tech")) {
				System.out.println(key);
				List<Technology> skillList = extractTechList(key);
				TechnologyCategory skills = new TechnologyCategory();
				skills.setCategoryName(key.replaceFirst("tech.", ""));
				skills.setTechList(skillList);
				stack.add(skills);
			}
		}

		
		return stack;
	}

	@Override
	public void setTechnologyStack(List<TechnologyCategory> stack) {
		// initialize property
		// Get Abstract superclass of Environment
		AbstractEnvironment absEnv = (AbstractEnvironment) env;
		
		// Read all keys
		Properties props = (Properties) absEnv.getPropertySources().get("content.props").getSource();
		
		// Delete all properties starts with tech
		for(String key: props.stringPropertyNames()) {
			if(key.startsWith("tech")) {
				props.remove(key);
			}
		}
		
		System.out.println("\n\n\n\n-----------------------------------");
		System.out.println("TechnologyDao : setTechnologyStack\n");
		System.out.println(props.stringPropertyNames()+"\n");
		System.out.println(stack);
		System.out.println("-------------------------------\n\n\n\n");
		
		// Populate new properties from parameter stack.
		for(TechnologyCategory techDto :stack) {
			String key =  "tech."+ techDto.getCategoryName();
			String techList = stringifyTechList(techDto.getTechList());
			props.setProperty(key, techList);
		}
		
		
		try {
			props.store(new FileOutputStream(basicContentSrc), null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


}
