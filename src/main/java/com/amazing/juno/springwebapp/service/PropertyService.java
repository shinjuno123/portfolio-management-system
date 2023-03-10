package com.amazing.juno.springwebapp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazing.juno.springwebapp.dao.AboutDao;
import com.amazing.juno.springwebapp.dao.ContactDao;
import com.amazing.juno.springwebapp.dao.FacePhotoDao;
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
	
	@Autowired
	FacePhotoDao facePhotoDao;

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


	@Override
	public void setIntroduction(IntroductionEntity intro) {
		introductionDao.setIntro(intro);
	}


	@Override
	public void setAbout(AboutEntity about) {
		aboutDao.SetAboutMe(about);
	}


	@Override
	public void setContactInfo(ContactEntity contact) {
		contactDao.setContactInfo(contact);
	}


	@Override
	public void setSnsLinks(Map<String, String> links) {
		snsLinksDao.setLinks(links);
	}


	@Override
	public void setTechnologyStack(List<TechnologyListDto> stack) {
		technologyDao.setTechnologyStack(stack);
	}


	@Override
	public MultipartFile getFacePhoto() {
		return facePhotoDao.getFacePhoto();
	}


	@Override
	public void setFacePhoto(MultipartFile file) {
		facePhotoDao.saveFacePhoto(file);
	}

}
