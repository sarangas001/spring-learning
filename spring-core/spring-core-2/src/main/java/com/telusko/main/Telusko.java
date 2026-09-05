package com.telusko.main;

import com.telusko.service.ICourse;

public class Telusko {
    private ICourse course;

    public Telusko() {
        System.out.println("Telusko bean created");
    }

    public Telusko(ICourse course) {
        this.course = course;
    }

    public void setCourse(ICourse course) {
        this.course = course;
    }
    public void buyTheCourse(double price) {
        this.course.buyTheCourse(price);
    }
}
