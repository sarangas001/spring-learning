package com.telusko.web_app_1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class TeluskoController {

    @GetMapping("/home")
    public String showHome()
    {

        return "home";
    }
    @GetMapping("/response")
    public String showCourses(Model model)
    {
        String[] books = new String[] {"Java", "DevOps", "AWS", "AI Engineering"};
        model.addAttribute("books", books);
//        for (String book: books) {
//            System.out.println(book);
//        }
        return "response";
    }
}
