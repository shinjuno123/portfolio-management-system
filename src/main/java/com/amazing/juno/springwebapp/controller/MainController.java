package com.amazing.juno.springwebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.amazing.juno.springwebapp.service.PropertyService;

@Controller
public class MainController {
	
	@Autowired
	PropertyService propertyService;
	
	@GetMapping("/")
	public String home(Model model) {
		System.out.println(propertyService.getIntroduction());
		return "index";
	}
	

}
