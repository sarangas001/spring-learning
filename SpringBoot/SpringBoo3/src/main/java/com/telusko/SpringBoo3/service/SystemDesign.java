package com.telusko.SpringBoo3.service;

import org.springframework.stereotype.Service;

@Service("sd")
public class SystemDesign implements ICourse{
    @Override
    public boolean setTheCourse() {
        return true;
    }
}
