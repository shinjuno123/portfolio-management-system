package com.amazing.juno.springwebapp.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.amazing.juno.springwebapp.entity.About;
import com.amazing.juno.springwebapp.entity.Contact;
import com.amazing.juno.springwebapp.entity.Introduction;
import com.amazing.juno.springwebapp.entity.Technology;
import com.amazing.juno.springwebapp.validator.IsEmpty;

import jakarta.validation.constraints.NotNull;

public class IntegratedInfo {
	@IsEmpty
	private Introduction intro;

	@IsEmpty
	private About about;
	
	@IsEmpty
	private Contact contact;
	
	@IsEmpty
	private Map<String, String> links;
	
	@NotNull(message = "Face Photo\n\n- You must provide your face photo!")
	private MultipartFile facePhoto;
	
	private String facePhotoURI;

	private String techs;
	
	private List<TechnologyCategory> convertedTechs;

	public IntegratedInfo() {}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Introduction getIntro() {
		return intro;
	}

	public void setIntro(Introduction intro) {
		this.intro = intro;
	}

	public About getAbout() {
		return about;
	}

	public void setAbout(About about) {
		this.about = about;
	}

	public Map<String, String> getLinks() {
		return links;
	}

	public void setLinks(Map<String, String> links) {
		this.links = links;
	}

	public String getTechs() {
		return techs;
	}

	private static List<TechnologyCategory> convertTechs(String tech) {
		System.out.println("\n\n\n\n\n-----------");
		System.out.println("Converting String to List<TechnologyListDto>...");
		
		String[] categories = tech.split("\n");
		List<TechnologyCategory> result = new ArrayList<>();
		
		System.out.println();
		for(String category : categories) {
			// Initialize TechnologyListDto object
			System.out.println("category : " + category);
			
			TechnologyCategory technologyList = new TechnologyCategory();
			
			String[] categoryNameandItems = category.split(" ");
			
			String categoryName = categoryNameandItems[0];
			System.out.println("categoryName : " + categoryName);
			
			String[] items = categoryNameandItems[1].split(",");
			
			// Initialize a instance "techList" variable of TechnologyListDto
			List<Technology> techList = new ArrayList<>();
			
			System.out.println("Length of items : " + items.length);
			for(String item : items) {
				System.out.println("Pure item : " + item);
				
				String[] keyandValue = item.split(":");
				
				String skill = keyandValue[0];
				Integer score = Integer.parseInt(keyandValue[1].strip());
				
				// Initialize TechnologyEntity and set values in this entity simultaneously
				Technology techEntity = new Technology(score, skill);
				System.out.println("skill&score : " + techEntity);
				// Add a TechnologyEntity instance to techList that is instance variable of TechnologyListDto
				techList.add(techEntity);
			}
			
			
			// Set each instance variable of TechnologyListDto instance
			technologyList.setCategoryName(categoryName);
			technologyList.setTechList(techList);
			System.out.println("TechnologyListDto instance : " + technologyList);
			
			// Add a TechnologyListDto instance to a list
			result.add(technologyList);
		}
		
		System.out.println("-----------\n\n\n\n\n");
		return result;
	}

	public void setTechs(String techs) {
		System.out.println("\n\n\n\n\n-----------");
		System.out.println("set Techs");
		System.out.println("techs=" + techs);

		System.out.println("-----------\n\n\n\n\n");
		this.techs = techs;
		this.convertedTechs = convertTechs(techs);
	}
	
	

	public List<TechnologyCategory> getConvertedTechs() {
		return convertedTechs;
	}

	public void setConvertedTechs(List<TechnologyCategory> convertedTechs) {
		this.convertedTechs = convertedTechs;
	}
	
	

	public MultipartFile getFacePhoto() {
		return facePhoto;
	}

	public void setFacePhoto(MultipartFile facePhoto) {
		this.facePhoto = facePhoto;
	}
	
	

	public String getFacePhotoURI() {
		return facePhotoURI;
	}

	public void setFacePhotoURI(String facePhotoURI) {
		this.facePhotoURI = facePhotoURI;
	}

	@Override
	public String toString() {
		return "IntegratedDto [intro=" + intro + "\nabout=" + about + "\ncontact=" + contact + "\nlinks=" + links
				+ "\ntechs=" + convertedTechs + "]";
	}

}
