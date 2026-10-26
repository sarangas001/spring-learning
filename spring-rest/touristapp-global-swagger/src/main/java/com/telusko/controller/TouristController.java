package com.telusko.controller;

import com.telusko.entity.Tourist;
import com.telusko.service.ITouristService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tourist")
@Tag(name = "Tourist Controller", description = "APIs for managing tourists")
public class TouristController {
    @Autowired
    private ITouristService service;

    @PostMapping("/register")
    @Operation(summary = "Register a new tourist", description = "Adds a new tourist to the system")
    public ResponseEntity<String> registerTourist(@RequestBody Tourist tourist)
    {
        String response = service.addTourist(tourist);
//        return ResponseEntity.created(response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/updateBudget/{id}/{budget}")
    @Operation(summary = "Update tourist budget", description = "Updates the budget of a tourist based on Id")
    public ResponseEntity<String> updateTouristBudget(@PathVariable("id") Integer id,@PathVariable("budget") Double budget)
    {

        String response = service.updateTourist(id, budget);
        return new ResponseEntity<String>(response, HttpStatus.CREATED);

    }

    @PutMapping("/updateTourist")
    @Operation(summary = "Update tourist information", description = "Updates the information of a tourist of a tourist based on the provided details")
    public ResponseEntity<String> updateTouristInfo(@RequestBody Tourist tourist)
    {
        String response = service.updateTourist(tourist);
        return new ResponseEntity<String>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteTourist/{id}")
    @Operation(summary = "Delete a tourist", description = "Deletes a tourist from the system based on Id")
    public ResponseEntity<String> deleteTourist(@PathVariable("id") Integer id)
    {
        String response = service.deleteTourist(id);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @GetMapping("/getTourist/{id}")
    @Operation(summary = "Get a tourist information", description = "get the information of a tourist of a tourist based on the Id")
    public ResponseEntity<?> getTourist(@PathVariable("id") Integer id)
    {
        Tourist tourist = service.getTourist(id);
        return new ResponseEntity<Tourist>(tourist, HttpStatus.OK);
    }

    @GetMapping("/getAllTourists")
    @Operation(summary = "Get all tourists information", description = "Gets all the information of a tourists")
    public ResponseEntity<List<Tourist>>  getAllTourists()
    {
        List<Tourist> list = service.getAllTourists();
        return new ResponseEntity<List<Tourist>>(list, HttpStatus.OK);
    }


}
