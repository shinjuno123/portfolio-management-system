package com.amazing.juno.springwebapp.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.amazing.juno.springwebapp.entity.AboutEntity;
import com.amazing.juno.springwebapp.entity.ContactEntity;
import com.amazing.juno.springwebapp.entity.IntroductionEntity;
import com.amazing.juno.springwebapp.entity.TechnologyEntity;
import com.amazing.juno.springwebapp.validator.IsEmpty;

import jakarta.validation.constraints.NotNull;

public class IntegratedDto {
	@IsEmpty
	private IntroductionEntity intro;

	@IsEmpty
	private AboutEntity about;
	
	@IsEmpty
	private ContactEntity contact;
	
	@IsEmpty
	private Map<String, String> links;
	
	@NotNull(message = "Face Photo\n\n- You must provide your face photo!")
	private MultipartFile facePhoto;
	
	private String facePhotoURI;

	private String techs;
	
	private List<TechnologyListDto> convertedTechs;

	public IntegratedDto() {}

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

	public String getTechs() {
		return techs;
	}

	private static List<TechnologyListDto> convertTechs(String tech) {
		System.out.println("\n\n\n\n\n-----------");
		System.out.println("Converting String to List<TechnologyListDto>...");
		
		String[] categories = tech.split("\n");
		List<TechnologyListDto> result = new ArrayList<>();
		
		System.out.println();
		for(String category : categories) {
			// Initialize TechnologyListDto object
			System.out.println("category : " + category);
			
			TechnologyListDto technologyList = new TechnologyListDto();
			
			String[] categoryNameandItems = category.split(" ");
			
			String categoryName = categoryNameandItems[0];
			System.out.println("categoryName : " + categoryName);
			
			String[] items = categoryNameandItems[1].split(",");
			
			// Initialize a instance "techList" variable of TechnologyListDto
			List<TechnologyEntity> techList = new ArrayList<>();
			
			System.out.println("Length of items : " + items.length);
			for(String item : items) {
				System.out.println("Pure item : " + item);
				
				String[] keyandValue = item.split(":");
				
				String skill = keyandValue[0];
				Integer score = Integer.parseInt(keyandValue[1].strip());
				
				// Initialize TechnologyEntity and set values in this entity simultaneously
				TechnologyEntity techEntity = new TechnologyEntity(score, skill);
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
	
	

	public List<TechnologyListDto> getConvertedTechs() {
		return convertedTechs;
	}

	public void setConvertedTechs(List<TechnologyListDto> convertedTechs) {
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
