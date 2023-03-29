package com.amazing.juno.springwebapp.validator;

import java.util.Map;

import com.amazing.juno.springwebapp.entity.About;
import com.amazing.juno.springwebapp.entity.Contact;
import com.amazing.juno.springwebapp.entity.Introduction;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsEmptyConstraintValidator implements ConstraintValidator<IsEmpty, Object>{
	
	private String message;
	
	public IsEmptyConstraintValidator() {
		message = "";
	}

	
	private boolean validateIntroductionEntity(Introduction intro) {
		StringBuilder messageStb = new StringBuilder();
		boolean isError = false;
		
		if(intro.getSayHi().strip().isBlank()) {
			messageStb.append(" - SayHi is Empty!\n");
			isError = true;
		}
		
		if(intro.getName().strip().isBlank()) {
			messageStb.append(" - YourName is Empty!\n");
			isError = true;	
		} 
		
		if(intro.getOpening().strip().isBlank()) {
			messageStb.append(" - Opening is Empty!\n");
			isError = true;
		} 
		
		if(intro.getDetail().strip().isBlank()) {
			messageStb.append(" - Detail is Empty!\n");
			isError = true;
		}
		
		if(isError) {
			message += "Introduction\n\n" + messageStb.toString();
		}
		
		
		return isError;
	}
	
	
	private boolean validateAboutEntity(About about){
		StringBuilder messageStb = new StringBuilder();
		boolean isError = false;
		
	
		if(about.getDescription().strip().isBlank()) {
			messageStb.append(" - AboutDetail is Empty!\n");
			isError = true;
		} 
		
		if(about.getPeriod().strip().isBlank()) {
			messageStb.append(" - Period is Empty!\n");
			isError = true;
		}
		
		if(about.getSchool().strip().isBlank()) {
			messageStb.append(" - School is Empty!\n");
			isError = true;
		}
		
		if(about.getDegree().strip().isBlank()) {
			messageStb.append(" - Degree is Empty!\n");
			isError = true;
		} 
		
		if(about.getRegionCountry().strip().isBlank()) {
			messageStb.append(" - RegionCountry is Empty!\n");
			isError = true;
		} 
		
		if(isError) {
			message += "About me\n\n" + messageStb.toString();
		}

		
		return isError;
		
	}
	
	private boolean validateContactEntity(Contact contact) {
		StringBuilder messageStb = new StringBuilder();
		boolean isError = false;
		
		if(contact.getClosingTitle().strip().isBlank()) {
			messageStb.append("- Closing Title is Empty!\n");
			isError = true;
		}
		
		if(contact.getClosingContent().strip().isBlank()) {
			messageStb.append("- Closing Content is Empty!\n");
			isError = true;
		}
		
		if(contact.getClosingRegard().strip().isBlank()) {
			messageStb.append("- Closing Regard is Empty!\n");
			isError = true;
		}
		
		if(contact.getButtonContent().strip().isBlank()) {
			messageStb.append("- Email Button Text is Empty!\n");
			isError = true;
		}
		
		if(contact.getEmail().strip().isBlank()) {
			messageStb.append("- Email is Empty!\n");
			isError = true;
		}
		
		if(isError) {
			message += "Contact\n\n" + messageStb.toString();
		}
		
		return isError;
	}
	
	
	private boolean validateSocialMediaLinks(Map<String, String> links) {
		StringBuilder messageStb = new StringBuilder();
		boolean isError = false;
		
		for(String key :links.keySet()) {
			if(links.get(key).strip().isBlank()) {
				messageStb.append("- "  + key + " is Empty!\n");
				isError = true;
			}
		}
		
		if(isError) {
			message += "Social Media\n\n" + messageStb.toString();
		}
		
		return isError;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Boolean isError = false; 
		message = "";
		
		context.disableDefaultConstraintViolation();
		
		if(value instanceof Introduction) {
			isError = validateIntroductionEntity((Introduction) value);
		}
		
		if (value instanceof About) {
			isError = validateAboutEntity((About) value);		
		}
		
		if(value instanceof Contact) {
			isError = validateContactEntity((Contact) value);
		}
		
		if(value instanceof Map) {
			System.out.println("\n\n\n\n\n-----------------------------------");
			System.out.println("Checking map!");
			isError = validateSocialMediaLinks((Map<String, String>) value);
			System.out.println("-----------------------------------\n\n\n\n");
		}
		
		context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
		

		return !isError;
	}


}
