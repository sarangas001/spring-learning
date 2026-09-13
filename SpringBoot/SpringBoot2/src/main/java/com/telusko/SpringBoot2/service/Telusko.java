package com.telusko.SpringBoot2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class Telusko implements IGreeting {
    @Autowired
    private LocalDateTime dateTime;

    @Override
    public String generateGreeting(String name) {
        return "Hey " +name +", Time is " + dateTime.toLocalTime();
    }
}
