package com.telusko.controller;

import com.telusko.model.Alien;
import com.telusko.service.Igreeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// @Controller + @ResponseBody ==> RestController
@RestController
public class GreetingController {
    @Autowired
    private Igreeting greet;

//    @GetMapping("/wish/{name}")
//    @ResponseBody
//    public String getGreeting(@PathVariable("name") String name)
//    {
//        String wish = greet.generateGreetings(name);
//        return wish;
//    }
    @GetMapping("/wish/{name}")
    public String getGreeting(@PathVariable("name") String name)
    {
        String wish = greet.generateGreetings(name);
        return wish;
    }

    @GetMapping("/wish2/{name}")
    public ResponseEntity<String> getGreeting2(@PathVariable("name") String name)
    {
        String wish = greet.generateGreetings(name);
        return new ResponseEntity<String>(wish, HttpStatus.OK);
    }

//    @GetMapping("/get-alien")
//    public ResponseEntity<Alien> getGreeting2()
//    {
//        Alien alien = new Alien(1, "Saketh", "Mubai");
//        return new ResponseEntity<Alien>(alien, HttpStatus.OK);
//    }

    @GetMapping("/get-alien")
    public Alien getGreeting2()
    {
        Alien alien = new Alien(1, "Saketh", "Mubi");
        return alien;
    }

    @PostMapping("/add-alien")
    public String addAlienInfo(@RequestBody Alien alien)
    {
        System.out.println(alien);
        return "Alien Object added";
    }

}
