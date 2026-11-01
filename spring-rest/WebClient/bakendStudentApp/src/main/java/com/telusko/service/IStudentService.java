package com.telusko.service;

import com.telusko.entity.Student;

import java.util.List;

public interface IStudentService {
    List<Student> getAllStudent();
    Student saveStudent(Student student);
    Student getStudentById(Long id);
    Student updateStudent(Student student);
    void deleteStudentById(Long id);
}
