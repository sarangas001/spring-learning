package com.telusko.service;

import com.telusko.entity.Student;
import com.telusko.repo.IStudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StudentService implements IStudentService{

    @Autowired
    private IStudentRepo repo;

    @Override
    public List<Student> getAllStudent() {
        return repo.findAll();
    }

    @Override
    public Student saveStudent(Student student) {
        return repo.save(student);
    }

    @Override
    public Student getStudentById(Long id) {

        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Student not found id : " + id));
    }

    @Override
    public Student updateStudent(Student student) {
        return repo.save(student);
    }

    @Override
    public void deleteStudentById(Long id) {
        Student student = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Student not found id : " + id));
        repo.deleteById(id);
    }
}
