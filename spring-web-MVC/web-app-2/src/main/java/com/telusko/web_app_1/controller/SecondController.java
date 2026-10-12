package com.telusko.web_app_1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("second")
public class SecondController {

    @GetMapping("/course-info")
    public String getCourseInfo(Model model) {
        model.addAttribute("message", "DevOps with AWS All tool recorded course is launched");
        return "index";
    }
}
