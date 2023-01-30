package com.amazing.juno.springwebapp.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.amazing.juno.springwebapp.entity.TechnologyEntity;

@Repository
public class TechnologyDao implements TechnologyInterface {
	
	@Autowired
	private Environment env;
	
	private List<TechnologyEntity> frontendList;
	private List<TechnologyEntity> backendList;

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


}
