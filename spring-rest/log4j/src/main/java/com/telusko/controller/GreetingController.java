package com.telusko.controller;

import com.telusko.service.IGreeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

//    private static final Logger log = LogManager.getLogger(GreetingController.class);

    private static final Logger log = LoggerFactory.getLogger(GreetingController.class);

    @Autowired
    private IGreeting service;

    @GetMapping("/api1")
    public ResponseEntity<String> generateGreetings()
    {
        log.info("Control in ap1");
        String wish = service.generateWishes("Saranga");
        log.debug("Service logic is been invoked and we got response");
        return new ResponseEntity<String>(wish, HttpStatus.OK);
    }

}
