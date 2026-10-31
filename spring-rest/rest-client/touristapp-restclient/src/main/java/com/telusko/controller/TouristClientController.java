package com.telusko.controller;

import com.telusko.dto.Tourist;
import com.telusko.service.TouristClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client/tourist")
@CrossOrigin(origins = "http://localhost:5173")
public class TouristClientController {

    private TouristClientService service;

    public TouristClientController(TouristClientService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public String register(@RequestBody Tourist tourist)
    {
        return service.registerTourist(tourist);
    }

    @GetMapping("/{id}")
    public Tourist getTourist(@PathVariable Integer id)
    {
        return service.getTourist(id);
    }

    @GetMapping("/all")
    public List<Tourist> getTourists()
    {
        return service.getAllTourists();
    }

    @DeleteMapping("/{id}")
    public String deleteTourist(@PathVariable("id") Integer id)
    {
        return service.deleteTourist(id);
    }

    @PutMapping
    public String updateTourist(@RequestBody Tourist tourist)
    {
        return service.updateTourist(tourist);
    }

    @PatchMapping("/{id}/{budget}")
    public String updateBudget(@PathVariable("id") Integer id, @PathVariable("budget") Double budget)
    {
        return service.updateBudget(id, budget);
    }


}
