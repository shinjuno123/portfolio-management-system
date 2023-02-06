package com.amazing.juno.springwebapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.amazing.juno.springwebapp.dto.IntegratedDto;
import com.amazing.juno.springwebapp.dto.TechnologyListDto;
import com.amazing.juno.springwebapp.entity.AboutEntity;
import com.amazing.juno.springwebapp.entity.ContactEntity;
import com.amazing.juno.springwebapp.entity.IntroductionEntity;
import com.amazing.juno.springwebapp.entity.TechnologyEntity;
import com.amazing.juno.springwebapp.service.PropertyService;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/admin")
@Controller
public class AdminController {
	
	@Autowired
	PropertyService propertyService;
	
	@GetMapping("/main")
	public String home(Model model) {
		System.out.println("Admin Page Loading....");
		IntroductionEntity intro = propertyService.getIntroduction();
		AboutEntity about = propertyService.getAbout();
		ContactEntity contact = propertyService.getContactInfo();
		List<TechnologyListDto> tech = propertyService.getTechnologyStack();
		Map<String,String> links = propertyService.getSnsLinks();
		IntegratedDto integrated = new IntegratedDto();
		
		integrated.setIntro(intro);
		integrated.setAbout(about);
		integrated.setContact(contact);
		
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
		model.addAttribute("integrated",integrated);
		return "admin";
	}
	
	
	@PostMapping("/main")
	public String saveChange(IntegratedDto integrated,HttpServletRequest request){
		System.out.println("\n----------------------");
		System.out.println(request.getRequestURL() + " " +  request.getMethod());
		System.out.println(integrated);
		System.out.println("----------------------\n");
		return "redirect:main";
	}

}
