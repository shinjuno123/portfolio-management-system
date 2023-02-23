package com.amazing.juno.springwebapp.validator;

import java.util.List;

import com.amazing.juno.springwebapp.dto.TechnologyListDto;
import com.amazing.juno.springwebapp.entity.TechnologyEntity;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsTechElemFormValidator implements ConstraintValidator<IsTechElemForm, List<?>>{

	
	@Override
	public boolean isValid(List<?> value, ConstraintValidatorContext context) {
		@SuppressWarnings("unchecked")
		List<TechnologyListDto> techForm = (List<TechnologyListDto>) value;
		System.out.println();
		System.out.println("------------------------");
		System.out.println("IsTechFormValid:" + techForm);
		System.out.println("------------------------\n");
		
		
		for (TechnologyListDto techItem : techForm) {
			// Check if techForm's category name is not empty.
			String categoryName = techItem.getCategoryName();
			if(categoryName.equals(null) || categoryName.isEmpty()) {
				customMessagerForValidation(context, "Category is Empty!");
			}
			
			System.out.println();
			System.out.println("CategoryName : " + techItem.getCategoryName());
			// Iterate technology skill list.
			for (TechnologyEntity tech : techItem.getTechList()) {
				// Check if Empty or Null techForm's skill list is exist.
				System.out.println("Skill :  " + tech.getSkill());
				System.out.println("Technology :  " + tech.getTechnologyDetail());
				if(isValidSkill() && isValidTechDetail()) {
					return true;
				}
			}
			
			System.out.println();
			
			
		}
		
		
		
		
		
		return false;
	}
	
	
	private void customMessagerForValidation(ConstraintValidatorContext context, String message) {
		context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
		
	}



	

}
