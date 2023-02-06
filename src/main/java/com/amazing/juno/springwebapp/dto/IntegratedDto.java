package com.amazing.juno.springwebapp.dto;

import java.util.List;
import java.util.Map;

import com.amazing.juno.springwebapp.entity.AboutEntity;
import com.amazing.juno.springwebapp.entity.ContactEntity;
import com.amazing.juno.springwebapp.entity.IntroductionEntity;
import com.amazing.juno.springwebapp.entity.TechnologyEntity;

public class IntegratedDto {
	IntroductionEntity intro;
	AboutEntity about;
	ContactEntity contact;
	Map<String, Map<String, List<TechnologyEntity>>> tech;

	public IntegratedDto() {
	}
	
	public Map<String, Map<String, List<TechnologyEntity>>> getTech() {
		return tech;
	}



	public void setTech(Map<String, Map<String, List<TechnologyEntity>>> tech) {
		this.tech = tech;
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
	
	@Override
	public String toString() {
		return "IntegratedDto [intro=" + intro + "\nabout=" + about + "\ncontact=" + contact + "\ntech=" + tech + "]";
	}

}
