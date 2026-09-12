package com.telusko.main;

import com.telusko.service.ICourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Telusko
{
    // Autowired - there have 3 type to auto wired
//    1. Fields
//    2. Setter
//    3. Constructor

    //Fields
//    @Autowired
//    @Qualifier("dev")
    private ICourse course;

    public Telusko() {
    }

    // constructor
    @Autowired
    public Telusko(@Qualifier("sd") ICourse course) {
        System.out.println("Telusko Bean created constructor");
        this.course = course;
    }

    public void buyTheCourse(double amount) {
        course.buyTheCourse(amount);
    }

    public ICourse getCourse() {
        return course;
    }

//    Setter method
/*    @Autowired*/
//    @Qualifier("dev")
    public void setCourse(ICourse course) {
        System.out.println("Telsuko Bean created setter");
        this.course = course;
    }
}
