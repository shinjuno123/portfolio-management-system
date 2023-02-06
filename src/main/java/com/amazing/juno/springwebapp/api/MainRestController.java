package com.amazing.juno.springwebapp.api;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazing.juno.springwebapp.dto.TechnologyListDto;

@RequestMapping("/api")
@RestController
public class MainRestController {
	
	@PostMapping("/technology-stacks")
	public List<TechnologyListDto> saveStack(@RequestBody List<TechnologyListDto> stacks){
		System.out.println("\n-----------------------");
		System.out.println(stacks);
		System.out.println("-----------------------\n");
		
		return stacks;
	}

}
