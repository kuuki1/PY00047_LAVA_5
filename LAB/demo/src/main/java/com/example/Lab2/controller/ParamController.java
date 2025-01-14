package com.example.Lab2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping
public class ParamController {

    @RequestMapping("/param/form")
    public String form() {
        return "paramform";
    }

    @PostMapping("/param/save/{x}")
    public String save(@PathVariable("x") String x, @RequestParam("y") String y, Model model) {
        model.addAttribute("x", x);
        model.addAttribute("y", y);
        return "paramform";
    }
}
