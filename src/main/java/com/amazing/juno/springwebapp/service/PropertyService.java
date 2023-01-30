package com.amazing.juno.springwebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazing.juno.springwebapp.dao.IntroductionDao;
import com.amazing.juno.springwebapp.entity.IntroductionEntity;

@Service
public class PropertyService implements PropertyServiceInterface{
	
	@Autowired
	IntroductionDao introductionDao;

	@Override
	public IntroductionEntity getIntroduction() {
	
		return introductionDao.getIntro();
	}

}
