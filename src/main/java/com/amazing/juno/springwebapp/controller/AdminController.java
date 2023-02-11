package com.amazing.juno.springwebapp.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.amazing.juno.springwebapp.dto.IntegratedDto;
import com.amazing.juno.springwebapp.dto.TechnologyListDto;
import com.amazing.juno.springwebapp.entity.AboutEntity;
import com.amazing.juno.springwebapp.entity.ContactEntity;
import com.amazing.juno.springwebapp.entity.IntroductionEntity;
import com.amazing.juno.springwebapp.service.PropertyService;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/admin")
@Controller
public class AdminController {
	
	@Autowired
	PropertyService propertyService;
	
	private Map<String,?> getAllDataNeededInAdmin(){
		IntroductionEntity intro = propertyService.getIntroduction();
		AboutEntity about = propertyService.getAbout();
		ContactEntity contact = propertyService.getContactInfo();
		List<TechnologyListDto> tech = propertyService.getTechnologyStack();
		Map<String,String> links = propertyService.getSnsLinks();
		IntegratedDto integrated = new IntegratedDto();
		
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
		
		return Map.ofEntries(
				Map.entry("integrated", integrated),
				Map.entry("tech",tech)
				);
	}
	
	@GetMapping("/main")
	public String home(Model model) {
		System.out.println("Admin Page Loading....");
		Map<String,?> allDataNeeded = getAllDataNeededInAdmin();
		
		model.addAllAttributes(allDataNeeded);
		return "admin";
	}
	
	
	@PostMapping("/main")
	public String saveChange(@Validated IntegratedDto integrated,HttpServletRequest request){
		System.out.println("\n----------------------");
		System.out.println(request.getRequestURL() + " " +  request.getMethod());
		System.out.println(integrated);
		System.out.println("----------------------\n");
		
		// send integratedDto's member variables to each Dao
		propertyService.setIntroduction(integrated.getIntro());
		propertyService.setAbout(integrated.getAbout());
		propertyService.setContactInfo(integrated.getContact());
		propertyService.setSnsLinks(integrated.getLinks());
	
		
		return "redirect:main";
	}
	
	
	@ExceptionHandler(BindException.class)
	public ModelAndView bindExceptionHandler(BindException e) {
		System.out.println("Admin Exception Handler Loading....");
		Map<String,?> allDataNeeded = getAllDataNeededInAdmin();
		Map<String,Map<String,String>> error = new HashMap<String,Map<String,String>>();
		
		e.getBindingResult().getFieldErrors().forEach(ex->{
			String[] messages = ex.getDefaultMessage().split("\n");
			Map<String,String> entity = new HashMap<>();
			
			for(String message:messages) {
				if(!message.equals("")) {
					String inputName = message.split(":")[0];
					String inputContent = message.split(":")[1];
					entity.put(inputName, inputContent);
				}
			}
			
			if(!entity.isEmpty()) {
				error.put(ex.getField(), entity);
			}
		});
		
		System.out.println("\n------------------");
		System.out.println("Received Error Message");
		System.out.println(error);
		System.out.println(e.getBindingResult().getFieldErrors());
		System.out.println("------------------\n");
		
		ModelAndView modelAndView =  new ModelAndView("admin");
		modelAndView.addAllObjects(allDataNeeded);
		modelAndView.addObject("error",error);
		
		
		return modelAndView;
	}

}
