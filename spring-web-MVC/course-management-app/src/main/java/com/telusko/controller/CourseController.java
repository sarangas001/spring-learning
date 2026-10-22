package com.telusko.controller;

import com.telusko.entity.Course;
import com.telusko.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CourseController {

    private ICourseService service;

    @Autowired
    public void setService(ICourseService service) {
        this.service = service;
    }

    // == List Courses ==
    @GetMapping("courselist")
    public String getAllCourses(Model model) {
        List<Course> list = service.getAllCourse();
        model.addAttribute("courses", list);
        return "courselist";
    }

    //==show Form ==
    @GetMapping("/course-form")
    public String showForm(@ModelAttribute("course") Course course)
    {
        return  "courseform";
    }

    //==update form ==
    @PutMapping("/updateCourseForm")
    public String updateForm(@ModelAttribute("course") Course course)
    {
        return "courseform";
    }

    // == register course ==
    @PostMapping("/register-course")
    public String registerCourse(@ModelAttribute("course") Course course)
    {
        service.registerCourse(course);
        return "redirect:/courselist";
    }

    @GetMapping("/delete-course")
    public String deleteCourse(@RequestParam("id") Integer id)
    {
        service.deleteCourse(id);
        return  "redirect:/courselist";
    }

}
