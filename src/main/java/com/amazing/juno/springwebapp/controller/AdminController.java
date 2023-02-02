package com.amazing.juno.springwebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminController {
	
	@GetMapping("")
	public String home(Model model) {
		System.out.println("Admin Page Loading....");
		return "admin";
	}

}
