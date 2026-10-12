package com.telusko.web_app_1.controller;

import com.telusko.web_app_1.model.Course;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class TeluskoController {
    @GetMapping("/course-info")
    public String getCourseInfo(Map<String, Object> map )
    {
        map.put("cid", 4);
        map.put("cname", "AI Engineering");
        map.put("cost", 2499.4);
        return "courseinfo";
    }

    @GetMapping("/course-details")
    public String getCourseInfo(Model model )
    {
        Course course = new Course(3, "DevOps", 2499.5);
        model.addAttribute("course", course);
        return "course";
    }

}
