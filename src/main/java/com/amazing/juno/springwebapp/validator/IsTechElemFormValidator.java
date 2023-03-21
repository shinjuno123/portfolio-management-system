package com.amazing.juno.springwebapp.validator;

import java.util.List;

import com.amazing.juno.springwebapp.dto.TechnologyCategory;
import com.amazing.juno.springwebapp.entity.Technology;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsTechElemFormValidator implements ConstraintValidator<IsTechElemForm, List<?>>{

	
	@Override
	public boolean isValid(List<?> value, ConstraintValidatorContext context) {
		@SuppressWarnings("unchecked")
		List<TechnologyCategory> techForm = (List<TechnologyCategory>) value;
		
		Boolean result = true;
		
		for (TechnologyCategory techItem : techForm) {
			// Check if techForm's category name is not empty.
			String categoryName = techItem.getCategoryName();
			if(categoryName.equals(null) || categoryName.isEmpty()) {
				customMessagerForValidation(context, "Category is Empty!");
				result = false;
			}
			
			System.out.println();
			System.out.println("CategoryName : " + techItem.getCategoryName());
			// Iterate technology skill list.
			for (Technology tech : techItem.getTechList()) {
				// Check if Empty or Null techForm's skill list is exist.
				System.out.println("Skill :  " + tech.getSkill());
				System.out.println("Score :  " + tech.getScore());
				if(!isValidScore(tech.getScore())) {
					result = false;
				}
			}
			
			System.out.println();
			
			
		}
		
		
		
		
		
		return result;
	}
	
	
	private boolean isValidScore(Integer score) {
		// Verify if techDetail is null or empty
		if(score < 1 || score > 5) {
			return false;
		}
		
		if(score.equals(null)) {
			return false;
		}
		
		return true;
	}


	private void customMessagerForValidation(ConstraintValidatorContext context, String message) {
		StringBuilder messageBuilder = new StringBuilder(context.getDefaultConstraintMessageTemplate());
		
		if(messageBuilder.toString().isBlank() || messageBuilder.toString().equals(null)) {
			messageBuilder.append(message);
		} else {
			messageBuilder.append("\n");
			messageBuilder.append(message);
		}
		

		context.buildConstraintViolationWithTemplate(messageBuilder.toString()).addConstraintViolation();
		
	}



	

}
