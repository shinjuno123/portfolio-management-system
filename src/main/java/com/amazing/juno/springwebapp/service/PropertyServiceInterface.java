package com.amazing.juno.springwebapp.service;

import java.util.List;
import java.util.Map;

import com.amazing.juno.springwebapp.dto.TechnologyListDto;
import com.amazing.juno.springwebapp.entity.AboutEntity;
import com.amazing.juno.springwebapp.entity.ContactEntity;
import com.amazing.juno.springwebapp.entity.IntroductionEntity;

public interface PropertyServiceInterface {
	public IntroductionEntity getIntroduction();
	public void setIntroduction(IntroductionEntity intro);
	
	public AboutEntity getAbout();
	public void setAbout(AboutEntity about);
	
	public ContactEntity getContactInfo();
	public void setContactInfo(ContactEntity contact);
	
	public List<TechnologyListDto> getTechnologyStack();
	public void setTechnologyStack(List<TechnologyListDto> stack);
	
	public Map<String,String> getSnsLinks();
	public void setSnsLinks(Map<String,String> links);
}
