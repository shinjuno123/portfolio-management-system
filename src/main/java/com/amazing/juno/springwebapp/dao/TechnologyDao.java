package com.amazing.juno.springwebapp.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.amazing.juno.springwebapp.dto.TechnologyListDto;
import com.amazing.juno.springwebapp.entity.TechnologyEntity;

@Repository
public class TechnologyDao implements TechnologyInterface {
	
	@Autowired
	private Environment env;
	

	public TechnologyDao() {
	
	}
	
	private List<TechnologyEntity> extractTechList(String key){
		String[] skills = env.getProperty(key, String[].class);
		List<TechnologyEntity> skillList = new ArrayList<>();
		
		// Fill techList
		for(int i=0; i< skills.length;i++) {
			String[] techDetail = skills[i].split("/");
			skillList.add(new TechnologyEntity(Integer.parseInt(techDetail[1]),techDetail[0]));
		}
		
		return skillList;
	}

	@Override
	public List<TechnologyListDto> getTechnologyStack() {
		// Get Abstract superclass of Environment
		AbstractEnvironment absEnv = (AbstractEnvironment) env;
		List<TechnologyListDto> stack = new LinkedList<>();
		
		// Read all keys
		Properties props = (Properties) absEnv.getPropertySources().get("content.props").getSource();
		for(String key: props.stringPropertyNames()) {
			// Extract a string starting with tech
			if(key.startsWith("tech")) {
				System.out.println(key);
				List<TechnologyEntity> skillList = extractTechList(key);
				TechnologyListDto skills = new TechnologyListDto();
				skills.setCategoryName(key.replaceFirst("tech.", ""));
				skills.setTechList(skillList);
				stack.add(skills);
			}
		}

		
		return stack;
	}


}
