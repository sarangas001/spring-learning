package com.telusko.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // default class name config
public class Config {
    public Config() {
        System.out.println("Config bean created");
    }

    @Bean
    public Password config1()
    {
        Password password = new Password("SHA");
        return  password;
    }
}
