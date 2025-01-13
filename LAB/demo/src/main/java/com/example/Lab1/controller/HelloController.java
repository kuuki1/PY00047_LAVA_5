package com.example.Lab1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping
public class HelloController {
    @RequestMapping("")
	public String hello(Model model) {
		model.addAttribute("title", "FPT Polytechnic");
		model.addAttribute("subject", "String Boot MVC");
		return "hello.html";
	}
}
