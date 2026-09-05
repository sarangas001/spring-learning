package com.telusko.service;

public class SystemDesign implements ICourse {

    public SystemDesign() {
        System.out.println("System Design bean created");
    }

    @Override
    public boolean buyTheCourse(double price) {
        return true;
    }
}
