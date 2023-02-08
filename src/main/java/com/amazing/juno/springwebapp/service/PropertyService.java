package com.amazing.juno.springwebapp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazing.juno.springwebapp.dao.AboutDao;
import com.amazing.juno.springwebapp.dao.ContactDao;
import com.amazing.juno.springwebapp.dao.IntroductionDao;
import com.amazing.juno.springwebapp.dao.SnsLinksDao;
import com.amazing.juno.springwebapp.dao.TechnologyDao;
import com.amazing.juno.springwebapp.dto.TechnologyListDto;
import com.amazing.juno.springwebapp.entity.AboutEntity;
import com.amazing.juno.springwebapp.entity.ContactEntity;
import com.amazing.juno.springwebapp.entity.IntroductionEntity;

@Service
public class PropertyService implements PropertyServiceInterface{
	
	@Autowired
	IntroductionDao introductionDao;
	
	@Autowired
	AboutDao aboutDao;
	
	@Autowired
	TechnologyDao technologyDao;
	
	@Autowired
	ContactDao contactDao;
	
	@Autowired
	SnsLinksDao snsLinksDao;

	@Override
	public IntroductionEntity getIntroduction() {
	
		return introductionDao.getIntro();
	}

	@Override
	public AboutEntity getAbout() {
		return aboutDao.getAboutMe();
	}


	@Override
	public ContactEntity getContactInfo() {
		return contactDao.getContactInfo();
	}

	@Override
	public List<TechnologyListDto> getTechnologyStack() {	
		return technologyDao.getTechnologyStack();
	}

	@Override
	public Map<String, String> getSnsLinks() {
		return snsLinksDao.getLinks();
	}

}
