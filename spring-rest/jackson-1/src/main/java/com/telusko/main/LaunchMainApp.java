package com.telusko.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.telusko.model.Alien;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

public class LaunchMainApp {
    public static void main(String[] args) throws JsonProcessingException {
        Alien alien = new Alien(1, "Rohan", "Bengaluru");
        Alien alien1 = new Alien(2, "Rohit", "Bengaluru");
        Alien alien2 = new Alien(3, "Ramesh", "Bengaluru");


        List<Alien> aliens = Arrays.asList(alien, alien1, alien2);
        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(alien);


        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(aliens);
        System.out.println(json);

    }
}
