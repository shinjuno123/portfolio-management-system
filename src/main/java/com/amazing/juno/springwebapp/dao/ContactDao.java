package com.amazing.juno.springwebapp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.amazing.juno.springwebapp.entity.ContactEntity;

@Repository
public class ContactDao implements ContactInterface{
	
	@Autowired
	Environment env;

	@Override
	public ContactEntity getContactInfo() {
		String title = env.getProperty("contact.title");
		String closing = env.getProperty("contect.closing");
		String appreciation = env.getProperty("contact.appreciation");
		String buttonContent = env.getProperty("contact.buttonContent");
		String email = env.getProperty("contact.email");
		
		ContactEntity contact = new ContactEntity(title, closing, appreciation, buttonContent, email);

		return contact;
	}
	
}
