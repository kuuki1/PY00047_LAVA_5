package com.example.Lab5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Lab5.services.CookieService;
import com.example.Lab5.services.ParamService;
import com.example.Lab5.services.SessionService;

@Controller
public class AccountController {
	@Autowired
	ParamService paramService;
	
	@Autowired
	CookieService cookieService;
	
	@Autowired
	SessionService sessionService;
	
	@GetMapping("/account/login")
	public String loadForm() {
		return "login";
	}
	
	@PostMapping("/account/login")
	public String login() {
		String user = paramService.getString("username", "");
		String password = paramService.getString("password", "");
		boolean rm = paramService.getBoolean("remember", false);
		
		if (user.equals("poly") && password.equals("123")) {
			//Session
			sessionService.set("user", user);
			
			String log = sessionService.get("user");
			System.out.println(log);
			
			//Cookie
			if (rm==true) {
				cookieService.set("user", user, 10);
			}
			else {
				cookieService.remove("user");
			}
		}
		
		return "login";
	}
}
