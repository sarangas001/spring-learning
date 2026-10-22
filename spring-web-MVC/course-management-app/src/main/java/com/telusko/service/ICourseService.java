package com.telusko.service;


import com.telusko.entity.Course;

import java.util.List;

public interface ICourseService {
    public List<Course> getAllCourse();
    public void registerCourse(Course course);
    public void deleteCourse(Integer id);
}
