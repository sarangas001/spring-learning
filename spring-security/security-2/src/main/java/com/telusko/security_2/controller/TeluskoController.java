package com.telusko.security_2.controller;

import com.telusko.security_2.model.Alien;
import com.telusko.security_2.model.Users;
import com.telusko.security_2.service.IUsersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TeluskoController {

    @Autowired
    private IUsersService service;

    BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(12);

    List<Alien> list = new ArrayList<>(List.of(new Alien(101, "Ravi"),
            new Alien(102, "Shivam"),
            new Alien(103, "Satyarth")));

    @GetMapping("/info")
    public ResponseEntity<String> getInfo(HttpServletRequest request)
    {
        String info = "Welcome to Agentic AI for java dev " + request.getSession().getId();
        System.out.println(info);
        return new ResponseEntity<>("Welcome to telusko", HttpStatus.OK);
    }

    @GetMapping("/course")
    public ResponseEntity<String> getCourseInfo(HttpServletRequest request)
    {
        String info = "Welcome to Agentic AI for java dev " + request.getSession().getId();
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @GetMapping("/aliens")
    public ResponseEntity<List<Alien>> getAlienInfo()
    {
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/addAlien")
    public void getAlienInfo(@RequestBody Alien alien)
    {
        list.add(alien);
        System.out.println(list);
    }

    @GetMapping("/csrf-token")
    public CsrfToken csrfToken(HttpServletRequest request)
    {
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        return token;
    }

    @PostMapping("/register")
    public Users registerUser(@RequestBody Users users)
    {
        users.setPassword(bcrypt.encode(users.getPassword())); // hash the password before saving
        return service.registerUser(users);
    }

}
