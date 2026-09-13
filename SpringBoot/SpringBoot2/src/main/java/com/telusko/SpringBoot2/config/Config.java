package com.telusko.SpringBoot2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class Config {
    public Config() {
        System.out.println("Config bean created");
    }

    @Bean
    public LocalDateTime generateDateTime() {
        return LocalDateTime.now();
    }

}
