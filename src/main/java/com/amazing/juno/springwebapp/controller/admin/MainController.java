package com.amazing.juno.springwebapp.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.amazing.juno.springwebapp.dto.IntegratedInfo;
import com.amazing.juno.springwebapp.dto.TechnologyCategory;
import com.amazing.juno.springwebapp.entity.About;
import com.amazing.juno.springwebapp.entity.Contact;
import com.amazing.juno.springwebapp.entity.Introduction;
import com.amazing.juno.springwebapp.service.PropertyServiceImpl;

@Controller
public class MainController {
	
	@Autowired
	PropertyServiceImpl propertyService;
	
	@GetMapping("/")
	public String home(Model model) {
		System.out.println("Main Page Loading....");
		Introduction intro = propertyService.getIntroduction();
		About about = propertyService.getAbout();
		List<TechnologyCategory> tech = propertyService.getTechnologyStack();
		Contact contact = propertyService.getContactInfo();
		Map<String,String> links = propertyService.getSnsLinks();
		
		IntegratedInfo integrated = new IntegratedInfo();
		
		integrated.setIntro(intro);
		integrated.setAbout(about);
		integrated.setContact(contact);
		integrated.setLinks(links);
		
		System.out.println("-------------------------");
		System.out.println("Introduction and About and Contact are loaded");
		System.out.println(integrated);
		System.out.println("-------------------------");
		
		
		System.out.println("-------------------------");
		System.out.println("technology is loaded");
		System.out.println(tech);
		System.out.println("-------------------------");

		
		model.addAttribute("tech",tech);
		model.addAttribute("integrated",integrated);
		return "index";
	}
	
	

}
