package com.telusko.springboot5.web;

import com.telusko.springboot5.repo.RepoLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class WebLayer {

    @Autowired
    private RepoLayer repo;

    public WebLayer() {
        System.out.println("Web Layer");
    }

    public void method2() {
        repo.disp();
    }
}
