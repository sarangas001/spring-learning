package com.telusko.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.telusko.model.Alien;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LaunchMainApp {
    public static void main(String[] args) throws IOException {


        ObjectMapper mapper = new ObjectMapper();

        Alien alien = mapper.readValue(new File("json/sample.json"), Alien.class);

        System.out.println(alien);


    }
}
