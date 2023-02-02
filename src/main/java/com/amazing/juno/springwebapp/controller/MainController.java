package com.amazing.juno.springwebapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.amazing.juno.springwebapp.entity.AboutEntity;
import com.amazing.juno.springwebapp.entity.ContactEntity;
import com.amazing.juno.springwebapp.entity.IntroductionEntity;
import com.amazing.juno.springwebapp.entity.TechnologyEntity;
import com.amazing.juno.springwebapp.service.PropertyService;

@Controller
public class MainController {
	
	@Autowired
	PropertyService propertyService;
	
	@GetMapping("/")
	public String home(Model model) {
		IntroductionEntity intro = propertyService.getIntroduction();
		AboutEntity about = propertyService.getAbout();
		Map<String, List<TechnologyEntity>> techFullstack = propertyService.getTechnology();
		ContactEntity contact = propertyService.getContactInfo();
		
		System.out.println("-------------------------");
		System.out.println("Introduction is loaded");
		System.out.println(intro);
		System.out.println("-------------------------");
		
		System.out.println("-------------------------");
		System.out.println("about is loaded");
		System.out.println(about);
		System.out.println("-------------------------");
		
		
		System.out.println("-------------------------");
		System.out.println("technology is loaded");
		System.out.println(techFullstack);
		System.out.println("-------------------------");
		
		
		System.out.println("-------------------------");
		System.out.println("contact is loaded");
		System.out.println(contact);
		System.out.println("-------------------------");
		
		model.addAttribute("intro", intro);
		model.addAttribute("about", about);
		model.addAllAttributes(techFullstack);
		model.addAttribute("contact", contact);
		return "index";
	}
	
	

}
