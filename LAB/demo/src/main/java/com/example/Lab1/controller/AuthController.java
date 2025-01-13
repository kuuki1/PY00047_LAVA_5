package com.example.Lab1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class AuthController {

    @GetMapping
    public String redirectToForm() {
        return "redirect:/login/form";
    }

    @GetMapping("/form")
    public String showLoginForm() {
        return "form";
    }

    @PostMapping("/check")
    public String checkLogin(@RequestParam String username, @RequestParam String password, Model model) {
        if ("poly".equals(username) && "123".equals(password)) {
            model.addAttribute("message", "Đăng nhập thành công!");
        } else {
            model.addAttribute("message", "Đăng nhập thất bại. Kiểm tra lại thông tin.");
        }

        return "form";
    }
}
