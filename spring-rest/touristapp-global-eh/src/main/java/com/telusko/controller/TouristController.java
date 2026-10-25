package com.telusko.controller;

import com.telusko.entity.Tourist;
import com.telusko.service.ITouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tourist")
public class TouristController {
    @Autowired
    private ITouristService service;

    @PostMapping("/register")
    public ResponseEntity<String> registerTourist(@RequestBody Tourist tourist)
    {
        String response = service.addTourist(tourist);
//        return ResponseEntity.created(response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/updateBudget/{id}/{budget}")
    public ResponseEntity<String> updateTouristBudget(@PathVariable("id") Integer id,@PathVariable("budget") Double budget)
    {

        String response = service.updateTourist(id, budget);
        return new ResponseEntity<String>(response, HttpStatus.CREATED);

    }

    @PutMapping("/updateTourist")
    public ResponseEntity<String> updateTouristInfo(@RequestBody Tourist tourist)
    {
        String response = service.updateTourist(tourist);
        return new ResponseEntity<String>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteTourist/{id}")
    public ResponseEntity<String> deleteTourist(@PathVariable("id") Integer id)
    {
        String response = service.deleteTourist(id);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @GetMapping("/getTourist/{id}")
    public ResponseEntity<?> getTourist(@PathVariable("id") Integer id)
    {
        Tourist tourist = service.getTourist(id);
        return new ResponseEntity<Tourist>(tourist, HttpStatus.OK);
    }

    @GetMapping("/getAllTourists")
    public ResponseEntity<?>  getAllTourists()
    {
        List<Tourist> list = service.getAllTourists();
        return new ResponseEntity<List<Tourist>>(list, HttpStatus.OK);
    }


}
