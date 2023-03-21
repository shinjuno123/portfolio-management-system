package com.amazing.juno.springwebapp.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.amazing.juno.springwebapp.dto.TechnologyCategory;
import com.amazing.juno.springwebapp.entity.About;
import com.amazing.juno.springwebapp.entity.Contact;
import com.amazing.juno.springwebapp.entity.Introduction;

public interface PropertyService {
	public Introduction getIntroduction();
	public void setIntroduction(Introduction intro);
	
	public About getAbout();
	public void setAbout(About about);
	
	public Contact getContactInfo();
	public void setContactInfo(Contact contact);
	
	public List<TechnologyCategory> getTechnologyStack();
	public void setTechnologyStack(List<TechnologyCategory> stack);
	
	public Map<String,String> getSnsLinks();
	public void setSnsLinks(Map<String,String> links);
	
	public String getFacePhotoPath();
	public void setFacePhoto(MultipartFile file);
	
}
