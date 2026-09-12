package com.telusko.service;

public class DevOps implements ICourse
{
    @Override
    public boolean buyTheCourse(double amount) {
        System.out.println("Successfully purchased the DevOps course amount is : " + amount);
        return true;
    }
}
