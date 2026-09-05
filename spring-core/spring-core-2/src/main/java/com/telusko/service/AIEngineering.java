package com.telusko.service;

public class AIEngineering implements ICourse {

    public AIEngineering() {
        System.out.println("AIEnginnering bean created");
    }

    @Override
    public boolean buyTheCourse(double price) {
        return true;
    }
}
