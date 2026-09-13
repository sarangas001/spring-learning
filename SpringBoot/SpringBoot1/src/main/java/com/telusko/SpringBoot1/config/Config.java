package com.telusko.SpringBoot1.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    public Config() {
        System.out.println("Config bean created");
    }
}
