package com.telusko.controller;


import com.telusko.entity.Student;
import com.telusko.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
// @CrossOrigin(origins = "http://localhost:5173")
public class StudentController {
    @Autowired
    private IStudentService service;

    @GetMapping("/Students")
    public ResponseEntity<List<Student>> getAllStudents()
    {
        return new ResponseEntity<List<Student>>(service.getAllStudent(), HttpStatus.OK);
    }

    @PostMapping("/Student")
    public ResponseEntity<Student> registerStudent(@RequestBody Student student)
    {
        return new ResponseEntity<Student>(service.saveStudent(student), HttpStatus.CREATED);
    }

    @GetMapping("/Student/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id)
    {
        Student student = service.getStudentById(id);
        return new ResponseEntity<Student>(student, HttpStatus.OK);
    }

    @PutMapping("/Student/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id, @RequestBody Student student)
    {
        Student st = service.getStudentById(id);
        st.setName(student.getName());
        st.setEmail(student.getEmail());
        st.setAddress(student.getAddress());
        st.setClassName(student.getClassName());
        st.setPhoneNumber(student.getPhoneNumber());
        st.setFathersName(student.getFathersName());

        Student updateStudent = service.updateStudent(student);
        return new ResponseEntity<Student>(updateStudent, HttpStatus.OK);
    }

    @DeleteMapping("/Student/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable Long id)
    {
        service.deleteStudentById(id);
        return new ResponseEntity<String>("Successfully, deleted the student", HttpStatus.OK);
    }

}
