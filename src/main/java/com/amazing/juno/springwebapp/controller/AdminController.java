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

import com.amazing.juno.springwebapp.dto.IntegratedInfo;
import com.amazing.juno.springwebapp.dto.NoteworthyProjectArray;
import com.amazing.juno.springwebapp.dto.TechnologyCategory;
import com.amazing.juno.springwebapp.dto.WorkDelete;
import com.amazing.juno.springwebapp.dto.WorkSave;
import com.amazing.juno.springwebapp.entity.About;
import com.amazing.juno.springwebapp.entity.Contact;
import com.amazing.juno.springwebapp.entity.Introduction;
import com.amazing.juno.springwebapp.entity.NoteworthyProject;
import com.amazing.juno.springwebapp.entity.Work;
import com.amazing.juno.springwebapp.exc.IntegratedExceptionMessage;
import com.amazing.juno.springwebapp.response.IntegratedResponseMessage;
import com.amazing.juno.springwebapp.service.PropertyServiceImpl;
import com.amazing.juno.springwebapp.service.WorkServiceImpl;

import jakarta.validation.Valid;

@RequestMapping("/admin")
@Controller
public class AdminController {
	
	@Autowired
	PropertyServiceImpl propertyService;

	@Autowired
	WorkServiceImpl workService;
	
	private IntegratedInfo getAllDataNeededInAdmin(){
		Introduction intro = propertyService.getIntroduction();
		About about = propertyService.getAbout();
		Contact contact = propertyService.getContactInfo();
		List<TechnologyCategory> tech = propertyService.getTechnologyStack();
		Map<String,String> links = propertyService.getSnsLinks();
		IntegratedInfo integrated = new IntegratedInfo();
		
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
		IntegratedInfo allDataNeeded = getAllDataNeededInAdmin();
		
		model.addAttribute("integrated",allDataNeeded);
		return "admin";
	}
	
	@GetMapping("/work")
	public ResponseEntity<List<Work>> getMainProjects(Model model){
		List<Work> works = workService.getWork();
		
		
		return new ResponseEntity<>(works, HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping("/work")
	public ResponseEntity<WorkSave> postMainProjects(@RequestBody @Valid WorkSave workList, BindingResult bindingResult){
		
		System.out.println("\n\n\n\n\n---------------------------------");
		System.out.println("admin/work POST");
		WorkSave result = new WorkSave();
		System.out.println(workList.getWorks()[workList.getWorks().length - 1]);
		result.setWorks(workList.getWorks());
		workService.saveOrUpdateWorks(workList.getWorks());
		System.out.println("--------------------------------------\n\n\n\n");
		
		return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/work")
	public ResponseEntity<WorkDelete> getMainProjects(@RequestBody WorkDelete workDeleteDto, BindingResult bindingResult){
		
		System.out.println("\n\n\n\n\n---------------------------------");
		workService.deleteWorks(workDeleteDto.getProjectIds());
		System.out.println("--------------------------------------\n\n\n\n");
		
		return new ResponseEntity<>(workDeleteDto, HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping("/noteworthy-project")
	public ResponseEntity<List<NoteworthyProject>> getNoteworthyProjects(){
		List<NoteworthyProject> projects = workService.findAllNoteworthyProjects();
		
		return new ResponseEntity<>(projects, HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping("/noteworthy-project")
	public ResponseEntity<NoteworthyProjectArray> saveNoteworthyProjects(@RequestBody @Valid NoteworthyProjectArray projects){
		System.out.println("\n\n\n\n\n---------------------------------");
		System.out.println("admin/noteworthy-project POST");
		System.out.println(projects);
		System.out.println("--------------------------------------\n\n\n\n");
		
		return new ResponseEntity<>(projects, HttpStatus.ACCEPTED);
	}
	
	
	
	
	
	
	@PostMapping("/main")
	public ResponseEntity<IntegratedResponseMessage> saveChange(@Valid IntegratedInfo integrated, BindingResult bindingResult){
		
		if(bindingResult.hasErrors()) {
			System.out.println("\n\n\n\n\n------------------------------------------");
			System.out.println("Form Validation Error");
			System.out.println(bindingResult.getAllErrors());
			System.out.println("------------------------------------------\n\n\n\n");
			throw new IntegratedExceptionMessage(bindingResult.getAllErrors());
		}
		
		IntegratedResponseMessage successResponse = new IntegratedResponseMessage();
		
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
	public ResponseEntity<IntegratedResponseMessage> bindExceptionHandler(IntegratedExceptionMessage e) {
		System.out.println("\n\n\n\n\n---------------------------------------------------------------");
		System.out.println("Admin Exception Handler Loading....\n");
		
		StringBuilder message = new StringBuilder();
		IntegratedResponseMessage errorResponse = new IntegratedResponseMessage();
		
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
