package com.teluso.spring_sec_demo.controller;

import com.teluso.spring_sec_demo.enity.Users;
import com.teluso.spring_sec_demo.service.IUsersService;
import com.teluso.spring_sec_demo.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private IUsersService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public Users register(@RequestBody Users users)
    {
        return service.register(users);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user)
    {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(user.getUsername());
            return new ResponseEntity<String>(token, HttpStatus.OK);
        }
        else
            return new ResponseEntity<String>("Login failed", HttpStatus.OK);
    }
}
