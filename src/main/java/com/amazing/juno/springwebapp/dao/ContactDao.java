package com.amazing.juno.springwebapp.dao;

import com.amazing.juno.springwebapp.entity.Contact;

public interface ContactDao {
	public Contact getContactInfo();
	public void setContactInfo(Contact contact);
}
