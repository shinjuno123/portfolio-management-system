package com.amazing.juno.springwebapp.service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazing.juno.springwebapp.dao.AboutDaoImpl;
import com.amazing.juno.springwebapp.dao.ContactDaoImpl;
import com.amazing.juno.springwebapp.dao.FacePhotoDaoImpl;
import com.amazing.juno.springwebapp.dao.IntroductionDaoImpl;
import com.amazing.juno.springwebapp.dao.SnsLinksDaoImpl;
import com.amazing.juno.springwebapp.dao.TechnologyDaoImpl;
import com.amazing.juno.springwebapp.dto.TechnologyCategory;
import com.amazing.juno.springwebapp.entity.About;
import com.amazing.juno.springwebapp.entity.Contact;
import com.amazing.juno.springwebapp.entity.Introduction;



@Service
public class PropertyServiceImpl implements PropertyService{
	
	@Autowired
	IntroductionDaoImpl introductionDao;
	
	@Autowired
	AboutDaoImpl aboutDao;
	
	@Autowired
	TechnologyDaoImpl technologyDao;
	
	@Autowired
	ContactDaoImpl contactDao;
	
	@Autowired
	SnsLinksDaoImpl snsLinksDao;
	
	@Autowired
	FacePhotoDaoImpl facePhotoDao;
	

	@Override
	public Introduction getIntroduction() {
		return introductionDao.getIntro();
	}
	

	@Override
	public About getAbout() {
		return aboutDao.getAboutMe();
	}


	@Override
	public Contact getContactInfo() {
		return contactDao.getContactInfo();
	}

	@Override
	public List<TechnologyCategory> getTechnologyStack() {	
		return technologyDao.getTechnologyStack();
	}

	@Override
	public Map<String, String> getSnsLinks() {
		return snsLinksDao.getLinks();
	}


	@Override
	public void setIntroduction(Introduction intro) {
		introductionDao.setIntro(intro);
	}


	@Override
	public void setAbout(About about) {
		aboutDao.SetAboutMe(about);
	}


	@Override
	public void setContactInfo(Contact contact) {
		contactDao.setContactInfo(contact);
	}


	@Override
	public void setSnsLinks(Map<String, String> links) {
		snsLinksDao.setLinks(links);
	}


	@Override
	public void setTechnologyStack(List<TechnologyCategory> stack) {
		technologyDao.setTechnologyStack(stack);
	}


	public String getFacePhotoPath() {
		String dataURI = null;
		try {
			dataURI = facePhotoDao.getFacePhoto();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return dataURI;
	}


	@Override
	public void setFacePhoto(MultipartFile file) {
		facePhotoDao.saveFacePhoto(file);
	}



}
