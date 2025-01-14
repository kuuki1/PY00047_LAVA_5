package com.example.Lab2.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.Lab2.entity.Product;

@Controller
public class ProductController {
    @GetMapping("/product/form")
    public String form(Model model) {
        Product p = new Product();
        p.setName("iPhone 30");
        p.setPrice(5000.0);
        model.addAttribute("product", p);
        return "productform";
    }

    @PostMapping("/product/save")
    public String save(@ModelAttribute("product") Product p, Model model) {
        model.addAttribute("savedProduct", p);
        return "productform";
    }

    @ModelAttribute("products")
    public List<Product> getItems() {
        return Arrays.asList(
            new Product("A", 1.0),
            new Product("B", 12.0),
            new Product("C", 20.0)
        );
    }
}

