package com.telusko.service;

import org.springframework.stereotype.Service;

@Service
public class ServiceLayer {

    public ServiceLayer() {
        System.out.println("Service Layer Bean created");
    }

    public void display() {
        System.out.println("Displayed");
    }
}

