package com.amazing.juno.springwebapp.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

@Repository
public class SnsLinksDao implements SnsLinksDaoInterface{
	
	@Autowired
	private Environment env;
	
	private Map<String, String> links;
	
	

	public SnsLinksDao() {
		links = new HashMap<String, String>();
	}



	@Override
	public Map<String, String> getLinks() {
		AbstractEnvironment absEnv = (AbstractEnvironment) env;
		
		Properties props = (Properties) absEnv.getPropertySources().get("content.props").getSource();
		for(String key: props.stringPropertyNames()) {
			if(key.startsWith("link")) {
				 links.put(key.replaceFirst("link.", ""), env.getProperty(key));
			}
		}
		
		return links;
	}

}
