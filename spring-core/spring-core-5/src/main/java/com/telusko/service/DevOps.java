package com.telusko.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service("dev")
@Primary
public class DevOps implements ICourse
{
    @Override
    public boolean buyTheCourse(double amount) {
        System.out.println("Successfully purchased the DevOps course amount is : " + amount);
        return true;
    }
}
