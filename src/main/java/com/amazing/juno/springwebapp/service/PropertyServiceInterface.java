package com.amazing.juno.springwebapp.service;

import java.util.List;
import java.util.Map;

import com.amazing.juno.springwebapp.entity.AboutEntity;
import com.amazing.juno.springwebapp.entity.ContactEntity;
import com.amazing.juno.springwebapp.entity.IntroductionEntity;
import com.amazing.juno.springwebapp.entity.TechnologyEntity;

public interface PropertyServiceInterface {
	public IntroductionEntity getIntroduction();
	public AboutEntity getAbout();
	public Map<String, List<TechnologyEntity>> getTechnology();
	public ContactEntity getContactInfo();
	public Map<String, Map<String, List<TechnologyEntity>>> getTechnologyStack();
	public Map<String,String> getSnsLinks();
}
