package com.telusko.SpringBoo3.service;

import org.springframework.stereotype.Service;

@Service("dev")
public class DevOps implements ICourse{
    @Override
    public boolean setTheCourse() {
        return true;
    }
}
