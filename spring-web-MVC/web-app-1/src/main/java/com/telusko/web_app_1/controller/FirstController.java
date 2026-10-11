package com.telusko.web_app_1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class FirstController
{
    // 1st way
    @RequestMapping("/telusko1")
    public ModelAndView displayInfo()
    {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", "This is my First Web App of SpringBoot Journey");
        mav.setViewName("index");

        return mav;
    }

    @GetMapping("/telusko2")
    public ModelAndView displayInfo1()
    {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", "This is my First Web App of SpringBoot Journey - GET Request");
        mav.setViewName("index");

        return mav;
    }

    // 2 way
    @GetMapping("/telusko3")
    public String displayInfo3(Model model)
    {
        BindingAwareModelMap bwp = null;
        System.out.println("Implementing class of model " + model.getClass().getName());
        model.addAttribute("message", "IPL Match KKR vs LSG");
        return "index";
    }

    @GetMapping("/telusko4")
    public String displayInfo4(Map map)
    {

        map.put("message", "IPL Match KKR vs LSG - loosely coupled");
        return "index";
    }

    // GET POST PUT DELETE PATCH
    // GetMapping PostMapping PatchMapping PutMapping DeleteMapping
}
