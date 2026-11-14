package com.teluso.spring_sec_demo.controller;

import com.teluso.spring_sec_demo.enity.Users;
import com.teluso.spring_sec_demo.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private IUsersService service;

    @PostMapping("/register")
    public Users register(@RequestBody Users users)
    {
        return service.register(users);
    }
}
