package com.telusko.service;

import com.telusko.entity.Course;
import com.telusko.repo.ICourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService implements ICourseService{

    @Autowired
    private ICourseRepo repo;


    @Override
    public List<Course> getAllCourse() {
        return (List<Course>) repo.findAll();
    }

    @Override
    public void registerCourse(Course course) {
        repo.save(course);
    }

    @Override
    public void deleteCourse(Integer id) {
        repo.deleteById(id);
    }
}
