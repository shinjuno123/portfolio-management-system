package com.amazing.juno.springwebapp.validator;

import com.amazing.juno.springwebapp.entity.IntroductionEntity;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IntegratedConstraintValidator implements ConstraintValidator<IntegratedValidator, Object>{
	

	@Override
	public void initialize(IntegratedValidator constraintAnnotation) {
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		
		if(value instanceof IntroductionEntity) {
			System.out.println("This input is an instance of IntroductionEntity!");
			System.out.println("This input is an instance of IntroductionEntity!");
			System.out.println("This input is an instance of IntroductionEntity!");
		}
		
		return true;
	}

}
