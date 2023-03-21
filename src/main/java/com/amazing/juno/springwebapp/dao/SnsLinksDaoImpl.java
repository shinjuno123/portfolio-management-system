package com.amazing.juno.springwebapp.dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

@Repository
public class SnsLinksDaoImpl implements SnsLinksDao{
	
	@Autowired
	private Environment env;
	
    @Value("${custom-property.basic-conetent}")
    private String basicContentSrc;
	
	private Map<String, String> links;
	
	

	public SnsLinksDaoImpl() {
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



	@Override
	public void setLinks(Map<String, String> links) {
		// Get Abstract superclass of Environment
		AbstractEnvironment absEnv = (AbstractEnvironment) env;
		
		// Read all keys
		Properties props = (Properties) absEnv.getPropertySources().get("content.props").getSource();
		
		for(String key : links.keySet()) {
			props.setProperty("link." + key, links.get(key));
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
