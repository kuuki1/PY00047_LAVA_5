package com.example.Lab1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/rectangle")
public class RectangleController {
    @RequestMapping("/form")
    public String showForm() {
        return "rectangleForm";
    }

    @PostMapping("/calculate")
    public String calculateAreaAndPerimeter(
            @RequestParam double length,
            @RequestParam double width,
            Model model) {

                if(length <= 0 || width <= 0){
                    model.addAttribute("message", "Chiều dài và chiều rộng không được nhỏ hơn 0!");
                    return "rectangleForm";
                }

                double dientich = length * width;
                double chuvi = 2 * (length + width);

                model.addAttribute("dientich", dientich);
                model.addAttribute("chuvi", chuvi);

                return "rectangleForm";
    }
}
