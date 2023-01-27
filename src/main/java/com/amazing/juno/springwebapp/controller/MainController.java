package com.amazing.juno.springwebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping("/")
	public String home(Model model) {
		
		return "index";
	}
	
	@GetMapping("/404")
	public String page404(Model model) {
		
		return "index";
	}
}
