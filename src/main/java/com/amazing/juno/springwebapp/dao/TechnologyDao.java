package com.amazing.juno.springwebapp.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.amazing.juno.springwebapp.entity.TechnologyEntity;

@Repository
public class TechnologyDao implements TechnologyInterface {
	
	@Autowired
	private Environment env;
	
	private List<TechnologyEntity> frontendList;
	private List<TechnologyEntity> backendList;
	Map<String, Map<String, List<TechnologyEntity>>> result;

	public TechnologyDao() {
		result = new LinkedHashMap<>();
	}

	@Override
	public List<TechnologyEntity> getFrontendList() {
		String[] frontend = env.getProperty("tech.frontend.skill&score",String[].class);
		frontendList = new ArrayList<>();
		

		// Fill frontendList
		for(int i=0; i< frontend.length;i++) {
			String[] techDetail = frontend[i].split("/");
			frontendList.add(new TechnologyEntity(Integer.parseInt(techDetail[1]),techDetail[0]));
		}
		

		return frontendList;
	}

	@Override
	public List<TechnologyEntity> getBackendList() {
		String[] backend = env.getProperty("tech.backend.skill&score",String[].class);
		backendList = new ArrayList<>();
		
		// Fill backendList
		for(int i=0; i< backend.length;i++) {
			String[] techDetail = backend[i].split("/");
			backendList.add(new TechnologyEntity(Integer.parseInt(techDetail[1]),techDetail[0]));
		}
		
		
		return backendList;
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
	public Map<String, Map<String, List<TechnologyEntity>>> getTechnologyStack() {
		// Get Abstract superclass of Environment
		AbstractEnvironment absEnv = (AbstractEnvironment) env;
		Map<String, List<TechnologyEntity>> stack = new LinkedHashMap<>();
		
		// Read all keys
		Properties props = (Properties) absEnv.getPropertySources().get("content.props").getSource();
		for(String key: props.stringPropertyNames()) {
			// Extract a string starting with tech
			if(key.startsWith("tech")) {
				System.out.println(key);
				List<TechnologyEntity> skillList = extractTechList(key);
				stack.put(key.substring(5), skillList);
			}
		}
		
		result.put("tech", stack);

		
		return result;
	}


}
