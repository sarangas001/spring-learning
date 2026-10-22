package com.telusko.repo;

import com.telusko.entity.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseRepo extends CrudRepository<Course, Integer> {
}
