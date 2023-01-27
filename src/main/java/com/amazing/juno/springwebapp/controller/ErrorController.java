package com.amazing.juno.springwebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
	@GetMapping("/error")
	public String handleError(Model model, HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
		if(status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			if(statusCode == 404) {
				model.addAttribute("title", "404 Error");
				model.addAttribute("message", "Page Not Found");
			}
		
		}
		
		return "error";
	}
}
