package com.telusko.SpringBoo3;

import com.telusko.SpringBoo3.service.ICourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Telusko {
    @Autowired
    @Qualifier("dev")
    private ICourse course;

    public Telusko() {
    }

    public void buyTheCourse() {
        System.out.println("Successfully, Purchased the course.");
    }

}
