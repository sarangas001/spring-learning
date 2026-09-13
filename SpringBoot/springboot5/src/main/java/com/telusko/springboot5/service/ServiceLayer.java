package com.telusko.springboot5.service;

import com.telusko.springboot5.repo.RepoLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceLayer {

    @Autowired
    private RepoLayer repo;

    public ServiceLayer() {
        System.out.println("Service Layer");
    }

    public void method1() {
        repo.disp();
    }
}
