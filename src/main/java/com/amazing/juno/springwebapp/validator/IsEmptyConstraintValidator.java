package com.amazing.juno.springwebapp.validator;

import com.amazing.juno.springwebapp.entity.AboutEntity;
import com.amazing.juno.springwebapp.entity.ContactEntity;
import com.amazing.juno.springwebapp.entity.IntroductionEntity;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsEmptyConstraintValidator implements ConstraintValidator<IsEmpty, Object>{
	
	private String message;
	
	public IsEmptyConstraintValidator() {
		message = "";
	}

	
	private boolean validateIntroductionEntity(IntroductionEntity intro) {
		StringBuilder messageStb = new StringBuilder();
		boolean isError = false;
		
		if(intro.getTitleMain().strip().isBlank()) {
			messageStb.append(" - SayHi is Empty!\n");
			isError = true;
		}
		
		if(intro.getMyName().strip().isBlank()) {
			messageStb.append(" - YourName is Empty!\n");
			isError = true;	
		} 
		
		if(intro.getSubTitle().strip().isBlank()) {
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
	
	
	private boolean validateAboutEntity(AboutEntity about){
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
	
	private boolean validateContactEntity(ContactEntity contact) {
		StringBuilder messageStb = new StringBuilder();
		boolean isError = false;
		
		if(contact.getTitle().strip().isBlank()) {
			messageStb.append("- Closing Title is Empty!\n");
			isError = true;
		}
		
		if(contact.getClosing().strip().isBlank()) {
			messageStb.append("- Closing Content is Empty!\n");
			isError = true;
		}
		
		if(contact.getAppreciation().strip().isBlank()) {
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

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Boolean isError = false; 
		message = "";
		
		context.disableDefaultConstraintViolation();
		
		if(value instanceof IntroductionEntity) {
			isError = validateIntroductionEntity((IntroductionEntity) value);
		}
		
		if (value instanceof AboutEntity) {
			isError = validateAboutEntity((AboutEntity) value);		
		}
		
		if(value instanceof ContactEntity) {
			isError = validateContactEntity((ContactEntity) value);
		}
		
		context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
		

		return !isError;
	}


}
