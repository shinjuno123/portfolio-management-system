package com.amazing.juno.springwebapp.validator;

import java.util.List;

import com.amazing.juno.springwebapp.dto.TechnologyListDto;
import com.amazing.juno.springwebapp.entity.TechnologyEntity;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsTechElemFormValidator implements ConstraintValidator<IsTechElemForm, List<TechnologyListDto>>{

	@Override
	public boolean isValid(List<TechnologyListDto> stack, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return false;
	}



	

}
