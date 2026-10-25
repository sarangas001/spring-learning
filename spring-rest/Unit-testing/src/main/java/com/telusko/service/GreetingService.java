package com.telusko.service;

import com.telusko.model.Alien;
import org.springframework.stereotype.Service;

@Service
public class GreetingService implements IGreeting{
    public String generateGreeting(String name) {
        return "Good Night, " + name;
    }

    @Override
    public Boolean addAlienInfo(Alien alien) {
        System.out.println(alien);
        return true;
    }
}
