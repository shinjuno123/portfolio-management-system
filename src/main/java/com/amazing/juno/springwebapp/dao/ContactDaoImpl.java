package com.amazing.juno.springwebapp.dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

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
		


		return Contact.builder().id(UUID.randomUUID())
				.closingTitle(title)
				.closingContent(closing)
				.closingRegard(appreciation)
				.buttonContent(buttonContent)
				.email(email)
				.build();
	}

	@Override
	public void setContactInfo(Contact contact) {
		
		// Get Abstract superclass of Environment

	}
	
}
