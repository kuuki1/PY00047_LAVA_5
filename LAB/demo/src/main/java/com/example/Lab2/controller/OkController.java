package com.example.Lab2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.qos.logback.core.model.Model;

@Controller
@RequestMapping
public class OkController {

    @RequestMapping("/ok")
    public String ok(Model model){
        return "okform";
    }

    @GetMapping("/ctrl/ok")
    @ResponseBody
    public String okGet(@RequestParam(name = "action", required = false) String action) {
        if ("m2".equals(action)) {
            return m2();
        }
        return "ok (GET)";
    }

    @PostMapping("/ctrl/ok")
    @ResponseBody
    public String okPost(@RequestParam(name = "action", required = false) String action) {
        if ("m1".equals(action)) {
            return m1();
        } else if ("m3".equals(action)) {
            return m3();
        }
        return "ok (POST)";
    }

    public String m1() {
        return "Method m1 called!";
    }

    public String m2() {
        return "Method m2 called!";
    }

    public String m3() {
        return "Method m3 called!";
    }
}
