package com.telusko.controller;

import com.telusko.model.Alien;
import com.telusko.service.IGreeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GreetingController {
    @Autowired
    private IGreeting service;

    @GetMapping("/wish/{name}")
    public String getGreetings(@PathVariable("name")String name)
    {
        String wish = service.generateGreeting(name);
        return wish;
    }

    @GetMapping("/wish2")
    public ResponseEntity<String> getGreetings2(String name)
    {
        String wish = service.generateGreeting(name);
        return new ResponseEntity<String>(wish, HttpStatus.OK);
    }




    @GetMapping("/get-alien")
    public Alien getAlienInfo()
    {
        Alien alien = new Alien(1, "Saranga", "Kadawatha");
        return alien;
    }

    @PostMapping("/add-alien")
    public ResponseEntity<Alien> addAlienInfo(@RequestBody Alien alien)
    {
        System.out.println(alien);
        service.addAlienInfo(alien);
        return new ResponseEntity<Alien>(alien, HttpStatus.CREATED);
    }

}
