package com.amazing.juno.springwebapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.amazing.juno.springwebapp.dto.TechnologyListDto;
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
		System.out.println("Main Page Loading....");
		IntroductionEntity intro = propertyService.getIntroduction();
		AboutEntity about = propertyService.getAbout();
		List<TechnologyListDto> tech = propertyService.getTechnologyStack();
		ContactEntity contact = propertyService.getContactInfo();
		Map<String,String> links = propertyService.getSnsLinks();
		
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
		System.out.println(tech);
		System.out.println("-------------------------");
		
		
		System.out.println("-------------------------");
		System.out.println("contact is loaded");
		System.out.println(contact);
		System.out.println("-------------------------");
		
		System.out.println("-------------------------");
		System.out.println("links are loaded");
		System.out.println(links);
		System.out.println("-------------------------");
		
		model.addAttribute("intro", intro);
		model.addAttribute("about", about);
		model.addAttribute("tech",tech);
		model.addAllAttributes(links);
		model.addAttribute("contact", contact);
		return "index";
	}
	
	

}
