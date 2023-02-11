package com.amazing.juno.springwebapp.validator;

import com.amazing.juno.springwebapp.entity.IntroductionEntity;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsEmptyConstraintValidator implements ConstraintValidator<IsEmpty, Object>{
	

	@Override
	public void initialize(IsEmpty constraintAnnotation) {
		constraintAnnotation.message();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		
		if(value instanceof IntroductionEntity) {
			IntroductionEntity intro = (IntroductionEntity) value;
			StringBuilder message = new StringBuilder();
			boolean isError = false;
			
			
			if(intro.getTitleMain().equals("")) {
				message.append("SayHi:is Empty!\n");
				isError = true;
			} else if(intro.getMyName().equals("")) {
				message.append("YourName:is Empty!\n");
				isError = true;
			} else if(intro.getSubTitle().equals("")) {
				message.append("Opening:is Empty!\n");
				isError = true;
			} else if(intro.getDetail().equals("")) {
				message.append("Detail:is Empty!\n");
				isError = true;
			}
			
			System.out.println("\n------------------");
			System.out.println("Error Message");
			System.out.println(message);
			System.out.println("------------------\n");
			
			if(isError) {
				customMessagerForValidation(context,message.toString());
				return false;
			}
			
		}
		
		return true;
	}

	private void customMessagerForValidation(ConstraintValidatorContext context, String message) {
		context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
		
	}

}
