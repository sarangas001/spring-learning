package com.telusko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GreetingService implements Igreeting{

    @Override
    public String generateGreetings(String name) {
        return "Good Night, " +  name ;
    }
}
