package com.telusko.hateoas_app_demo.controller;

import com.telusko.hateoas_app_demo.model.Course;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {
    @GetMapping("/course")
    public ResponseEntity<Course> getCourseInfo()
    {
        Course course = new Course(1, "Java", "Telusko", 1000);
//        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CourseController.class).getAiInfo())
//                .withRel("get-java");
//        course.add(link);

        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CourseController.class).getAiMoreInfo())
                .withRel("get-more-java");

        course.add(link);
        return new ResponseEntity<Course>(course, HttpStatus.OK);
    }

    @GetMapping("/get-java")
    public ResponseEntity<List<Course>> getAiInfo()
    {
        Course course = new Course(1, "AI Engineering", "Telusko", 1200);
        List<Course> list = List.of(new Course(3, "Java", "Telusko", 1000), new Course(3, "AI Engineering", "Telusko", 1000), new Course(3, "DevOps","Telusko", 1523));
        return new ResponseEntity<List<Course>>(list, HttpStatus.OK);
    }

    @GetMapping("/get-more-java")
    public ResponseEntity<List<Course>> getAiMoreInfo()
    {
        Course course = new Course(1, "AI Engineering", "Telusko", 1200);
        List<Course> list = List.of(new Course(5, "System Design", "Telusko", 1000), new Course(3, "AI Engineering", "Telusko", 1000), new Course(3, "DevOps","Telusko", 1523));
        return new ResponseEntity<List<Course>>(list, HttpStatus.OK);
    }

//    @GetMapping("/course")
//    public ResponseEntity<Course> getJavaInfo()
//    {
//        Course course = new Course(1, "Java", "Telusko", 1000);
//        return new ResponseEntity<Course>(course, HttpStatus.OK);
//    }



}
