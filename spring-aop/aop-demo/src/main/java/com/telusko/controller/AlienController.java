package com.telusko.controller;

import com.telusko.entity.Alien;
import com.telusko.service.IAlienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlienController
{

    @Autowired
    private IAlienService service;

    @PostMapping("/addAlien")
    public ResponseEntity<Alien> registerAlien(@RequestBody Alien alien)
    {
        System.out.println("AlienController: registerAlien method called");
        Alien alien1 = service.registerAlien(alien);
        return new ResponseEntity<Alien>(alien1, HttpStatus.CREATED);
    }

    @GetMapping("/getAllAliens")
    public ResponseEntity<List<Alien>> getAllAlien()
    {
        List<Alien> list = service.getAllAliens();
        return new ResponseEntity<List<Alien>>(list, HttpStatus.OK);
    }

}
