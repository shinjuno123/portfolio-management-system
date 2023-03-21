package com.amazing.juno.springwebapp.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.amazing.juno.springwebapp.dto.IntegratedDto;
import com.amazing.juno.springwebapp.dto.TechnologyListDto;
import com.amazing.juno.springwebapp.dto.WorkDeleteDto;
import com.amazing.juno.springwebapp.dto.WorkListDto;
import com.amazing.juno.springwebapp.entity.AboutEntity;
import com.amazing.juno.springwebapp.entity.ContactEntity;
import com.amazing.juno.springwebapp.entity.IntroductionEntity;
import com.amazing.juno.springwebapp.entity.Work;
import com.amazing.juno.springwebapp.exc.IntegratedRequestException;
import com.amazing.juno.springwebapp.response.IntegratedRequestResponse;
import com.amazing.juno.springwebapp.service.PropertyService;
import com.amazing.juno.springwebapp.service.WorkServiceImpl;

import jakarta.validation.Valid;

@RequestMapping("/admin")
@Controller
public class AdminController {
	
	@Autowired
	PropertyService propertyService;
	
	@Autowired
	WorkServiceImpl workService;
	
	private IntegratedDto getAllDataNeededInAdmin(){
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
		integrated.setConvertedTechs(tech);
		integrated.setFacePhotoURI(propertyService.getFacePhotoPath());
		
		System.out.println("\n\n\n\n\n-------------------------");
		System.out.println("Introduction and About and Contact are loaded");
		System.out.println(integrated);
		System.out.println("-------------------------\n\n\n\n");
		
		return integrated;
	}
	
	@GetMapping("/main")
	public String home(Model model) {
		System.out.println("Admin Page Loading....");
		IntegratedDto allDataNeeded = getAllDataNeededInAdmin();
		
		model.addAttribute("integrated",allDataNeeded);
		return "admin";
	}
	
	@GetMapping("/work")
	public ResponseEntity<List<Work>> getMainProjects(Model model){
		List<Work> works = workService.getWork();
		
		
		return new ResponseEntity<>(works, HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping("/work")
	public ResponseEntity<WorkListDto> postMainProjects(@RequestBody WorkListDto workList, BindingResult bindingResult){
		
		System.out.println("\n\n\n\n\n---------------------------------");
		System.out.println("admin/work");
		WorkListDto result = new WorkListDto();
		result.setWorks(workList.getWorks());
		workService.saveOrUpdate(workList.getWorks());
		System.out.println("--------------------------------------\n\n\n\n");
		
		return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/work")
	public ResponseEntity<WorkDeleteDto> getMainProjects(@RequestBody WorkDeleteDto workDeleteDto, BindingResult bindingResult){
		
		System.out.println("\n\n\n\n\n---------------------------------");
		workService.delete(workDeleteDto.getProjectIds());
		System.out.println("--------------------------------------\n\n\n\n");
		
		return new ResponseEntity<>(workDeleteDto, HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping("/main")
	public ResponseEntity<IntegratedRequestResponse> saveChange(@Valid IntegratedDto integrated, BindingResult bindingResult){
		
		if(bindingResult.hasErrors()) {
			System.out.println("\n\n\n\n\n------------------------------------------");
			System.out.println("Form Validation Error");
			System.out.println(bindingResult.getAllErrors());
			System.out.println("------------------------------------------\n\n\n\n");
			throw new IntegratedRequestException(bindingResult.getAllErrors());
		}
		
		IntegratedRequestResponse successResponse = new IntegratedRequestResponse();
		
		System.out.println("\n\n\n----------------------");
		System.out.println("Save change");
		System.out.println(integrated);
		System.out.println(integrated.getTechs());
		System.out.println("----------------------\n\n\n");
		
		// send integratedDto's instance variables to each Dao
		propertyService.setIntroduction(integrated.getIntro());
		propertyService.setAbout(integrated.getAbout());
		propertyService.setContactInfo(integrated.getContact());
		propertyService.setSnsLinks(integrated.getLinks());
		propertyService.setTechnologyStack(integrated.getConvertedTechs());
		propertyService.setFacePhoto(integrated.getFacePhoto());
		
		// Set response instance variables
		successResponse.setStatus(HttpStatus.ACCEPTED.value());
		successResponse.setMessage("succeeded to save your information!");
		successResponse.setTimeStamp(System.currentTimeMillis());
		
		
		return new ResponseEntity<>(successResponse, HttpStatus.ACCEPTED);
	}
	
	
	@ExceptionHandler
	public ResponseEntity<IntegratedRequestResponse> bindExceptionHandler(IntegratedRequestException e) {
		System.out.println("\n\n\n\n\n---------------------------------------------------------------");
		System.out.println("Admin Exception Handler Loading....\n");
		
		StringBuilder message = new StringBuilder();
		IntegratedRequestResponse errorResponse = new IntegratedRequestResponse();
		
		for(ObjectError error: e.getErrors()) {
			message.append(error.getDefaultMessage());
			message.append("\n\n");
		}
		
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage(message.toString());
		errorResponse.setTimeStamp(System.currentTimeMillis());
		
		System.out.println("---------------------------------------------------------------");
		
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

}
