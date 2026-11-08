package com.telusko.controller;

import com.telusko.model.Alien;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TeluskoController {

    List<Alien> list = new ArrayList<>(List.of(new Alien(101, "Ravi")
            , new Alien(102, "Shivam")
            , new Alien(103, "Satyarth")
    ));


    @GetMapping("/info")
    public ResponseEntity<String> getInfo(HttpServletRequest request)
    {
        String info = "Welcome to Agentic AI for java dev" + request.getSession().getId();
        System.out.println(info);
        return new ResponseEntity<>("Welcome to Teluso ", HttpStatus.OK);
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
        return ResponseEntity.ok(list);
    }

    @PostMapping("/addAlien")
    public void getAlienInfo(@RequestBody Alien a)
    {
        System.out.println(a);
        list.add(a);
        System.out.println(list);
    }

    @GetMapping("/csrf-token")
    public CsrfToken csrfToken(HttpServletRequest request)
    {
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        return token;
    }

}
