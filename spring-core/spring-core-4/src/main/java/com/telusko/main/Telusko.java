package com.telusko.main;

import com.telusko.service.ICourse;

public class Telusko
{
    private ICourse course;

    public Telusko() {
    }

    public Telusko(ICourse course) {
        System.out.println("Telusko Bean created constructor");
        this.course = course;
    }

    public void buyTheCourse(double amount) {
        course.buyTheCourse(amount);
    }

    public ICourse getCourse() {
        return course;
    }

    public void setCourse(ICourse course) {
        System.out.println("Telsuko Bean created setter");
        this.course = course;
    }
}
