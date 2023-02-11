package com.amazing.juno.springwebapp.dto;

import java.util.Map;

import com.amazing.juno.springwebapp.entity.AboutEntity;
import com.amazing.juno.springwebapp.entity.ContactEntity;
import com.amazing.juno.springwebapp.entity.IntroductionEntity;
import com.amazing.juno.springwebapp.validator.IsEmpty;

public class IntegratedDto {
	@IsEmpty
	IntroductionEntity intro;
	
	
	AboutEntity about;
	ContactEntity contact;
	Map<String,String> links;

	public IntegratedDto() {
	}
	
	public ContactEntity getContact() {
		return contact;
	}

	public void setContact(ContactEntity contact) {
		this.contact = contact;
	}

	public IntroductionEntity getIntro() {
		return intro;
	}

	public void setIntro(IntroductionEntity intro) {
		this.intro = intro;
	}

	public AboutEntity getAbout() {
		return about;
	}

	public void setAbout(AboutEntity about) {
		this.about = about;
	}
	
	public Map<String, String> getLinks() {
		return links;
	}

	public void setLinks(Map<String, String> links) {
		this.links = links;
	}

	@Override
	public String toString() {
		return "IntegratedDto [intro=" + intro + "\nabout=" + about + "\ncontact=" + contact + "\nlinks=" + links + "]";
	}

}
