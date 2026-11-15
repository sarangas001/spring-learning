package com.teluso.spring_sec_demo.controller;

import com.teluso.spring_sec_demo.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    List<Student> list = new ArrayList<>(List.of(
            new Student(1, "Navin", "Java"),
            new Student(2, "Kiran", "Blockchain")
    ));

    @GetMapping("/student")
    public ResponseEntity<List<Student>> getStudents()
    {
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/student")
    public void getStudents(@RequestBody Student student)
    {
        list.add(student);
    }

    @GetMapping("/csrf-token")
    public ResponseEntity<CsrfToken> csrfToken(HttpServletRequest request)
    {
        return new ResponseEntity<>( ( (CsrfToken) request.getAttribute("_csrf")), HttpStatus.OK);
    }

}
