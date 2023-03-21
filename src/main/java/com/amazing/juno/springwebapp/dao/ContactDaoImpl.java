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

import com.amazing.juno.springwebapp.entity.Contact;

@Repository
public class ContactDaoImpl implements ContactDao{
	
	@Autowired
	Environment env;
	
    @Value("${custom-property.basic-conetent}")
    private String basicContentSrc;

	@Override
	public Contact getContactInfo() {
		String title = env.getProperty("contact.title");
		String closing = env.getProperty("contect.closing");
		String appreciation = env.getProperty("contact.appreciation");
		String buttonContent = env.getProperty("contact.buttonContent");
		String email = env.getProperty("contact.email");
		
		Contact contact = new Contact(title, closing, appreciation, buttonContent, email);

		return contact;
	}

	@Override
	public void setContactInfo(Contact contact) {
		
		// Get Abstract superclass of Environment
		AbstractEnvironment absEnv = (AbstractEnvironment) env;
		
		// Read all keys
		Properties props = (Properties) absEnv.getPropertySources().get("content.props").getSource();
		

		props.setProperty("contact.title", contact.getTitle());
		props.setProperty("contect.closing", contact.getClosing());
		props.setProperty("contact.appreciation", contact.getAppreciation());
		props.setProperty("contact.buttonContent", contact.getButtonContent());
		props.setProperty("contact.email", contact.getEmail());

		try {
			props.store(new FileOutputStream(basicContentSrc), null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
