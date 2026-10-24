package com.telusko.service;

import com.telusko.controller.GreetingController;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GreetingService implements IGreeting{

    private static final Logger log = LoggerFactory.getLogger(GreetingController.class);

    public String generateWishes(String name) {
        log.info("SERVICE / Business logic is been invoked");
        return "Good Morning, " + name ;
    }
}
