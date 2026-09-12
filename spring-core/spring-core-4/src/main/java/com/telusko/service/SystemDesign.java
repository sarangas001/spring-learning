package com.telusko.service;

public class SystemDesign implements ICourse
{
    @Override
    public boolean buyTheCourse(double amount) {
        System.out.println("Successfully purchased the System Design course amount is : " + amount);
        return true;
    }
}
