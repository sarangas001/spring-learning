package com.telusko.SpringBoot1.service;

import org.springframework.stereotype.Service;

@Service
public class ServiceLayer {
    public ServiceLayer() {
        System.out.println("Service Bean created");
    }
}
