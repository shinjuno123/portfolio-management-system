package com.amazing.juno.springwebapp.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazing.juno.springwebapp.dto.TechnologyListDto;
import com.amazing.juno.springwebapp.service.PropertyService;
import com.amazing.juno.springwebapp.validator.IsTechElemForm;

@RequestMapping("/api")
@RestController
public class MainRestController {
	
	@Autowired
	PropertyService propertyService;
	
	@PostMapping("/technology-stacks")
	public List<TechnologyListDto> saveStack(@IsTechElemForm @RequestBody List<TechnologyListDto> stacks){
		System.out.println("\n-----------------------");
		System.out.println(stacks);
		System.out.println("-----------------------\n");
		
		// send serialized data to service layer
		propertyService.setTechnologyStack(stacks);
		
		return stacks;
	}

}
