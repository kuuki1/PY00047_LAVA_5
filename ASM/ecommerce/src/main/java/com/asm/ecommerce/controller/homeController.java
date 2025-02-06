package com.asm.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homeController {
    
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @GetMapping("/products")
    public String products(){
        return "product";
    }

    @GetMapping("/product")
    public String viewProducts(){
        return "product-detail";
    }

    @GetMapping("/forgotPass")
    public String forgotPass(){
        return"forgot_password";
    }

    @GetMapping("/terms-and-conditions")
    public String termsAndConditions() {
        return "terms-and-conditions";
    }

    @GetMapping("/privacy-policy")
    public String privacyPolicy() {
        return "privacy-policy";
    }

    @GetMapping("/copyright")
    public String copyright() {
        return "copyright";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @GetMapping("/account")
    public String account() {
        return "account";
    }
}
