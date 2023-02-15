package com.amazing.juno.springwebapp.validator;

import java.util.Map;

import com.amazing.juno.springwebapp.entity.AboutEntity;
import com.amazing.juno.springwebapp.entity.IntroductionEntity;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsEmptyConstraintValidator implements ConstraintValidator<IsEmpty, Object>{
	

	@Override
	public void initialize(IsEmpty constraintAnnotation) {
		constraintAnnotation.message();
	}
	
	private Map<String,?> validateIntroductionEntity(IntroductionEntity intro) {
		StringBuilder message = new StringBuilder();
		boolean isError = false;
		
	
		if(intro.getTitleMain().strip().isBlank()) {
			message.append("SayHi:is Empty!\n");
			isError = true;
		} else {
			message.append("SayHi: \n");
		}
		if(intro.getMyName().strip().isBlank()) {
			message.append("YourName:is Empty!\n");
			isError = true;	
		} else {
			message.append("YourName: \n");
		}
		if(intro.getSubTitle().strip().isBlank()) {
			message.append("Opening:is Empty!\n");
			isError = true;
		} else {
			message.append("Opening: \n");
		}
		
		if(intro.getDetail().strip().isBlank()) {
			message.append("Detail:is Empty!\n");
			isError = true;
		} else {
			message.append("Detail: \n");
		}
		
		System.out.println("\n------------------");
		System.out.println("Error Message");
		System.out.println(message);
		System.out.println("------------------\n");
		
		return Map.ofEntries(
				Map.entry("message", message.toString()),
				Map.entry("isError", isError)
				);
	}
	
	
	private Map<String,?> validateAboutEntity(AboutEntity about){
		StringBuilder message = new StringBuilder();
		boolean isError = false;
		
	
		if(about.getDescription().strip().isBlank()) {
			message.append("AboutDetail:is Empty!\n");
			isError = true;
		} else {
			message.append("AboutDetail: \n");
		}
		

		
		System.out.println("\n------------------");
		System.out.println("Error Message");
		System.out.println(message);
		System.out.println("------------------\n");
		
		return Map.ofEntries(
				Map.entry("message", message.toString()),
				Map.entry("isError", isError)
				);
		
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		
		if(value instanceof IntroductionEntity) {
			Map<String,?> result = validateIntroductionEntity((IntroductionEntity) value);

			
			if((Boolean) result.get("isError")) {
				customMessagerForValidation(context,(String) result.get("message"));
				return false;
			}
			
			
		}
		
		if (value instanceof AboutEntity) {
			Map<String,?> result = validateAboutEntity((AboutEntity) value);
			
			if((Boolean) result.get("isError")) {
				customMessagerForValidation(context,(String) result.get("message"));
				return false;
			}
		}
	
		
		return true;
	}

	private void customMessagerForValidation(ConstraintValidatorContext context, String message) {
		context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
		
	}

}
