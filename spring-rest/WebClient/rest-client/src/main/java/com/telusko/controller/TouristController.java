package com.telusko.controller;

import com.telusko.dto.Tourist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Controller
public class TouristController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient webClient;

    private final String BASE_URL = "http://localhost:8080/tourist";

    @GetMapping("/")
    public String home(Model model)
    {
        String url = BASE_URL + "/getAllTourists";
//        List<Tourist> list = Arrays.asList(restTemplate.getForObject(url, Tourist[].class));
        List<Tourist> list = webClient.get().uri("/getAllTourists").retrieve()
                .bodyToFlux(Tourist.class)
                .collectList().block();
        model.addAttribute("tourists", list);
        return "index";
    }

    @PostMapping("/add")
    public String addTourist(@ModelAttribute Tourist tourist, Model model)
    {
        String url = BASE_URL + "/register";
//        String response = restTemplate.postForObject(url, tourist, String.class);
        String response = webClient.post().uri("/register").bodyValue(tourist)
                .retrieve()
                .bodyToMono(String.class).block();
        model.addAttribute("message", response);
        return "redirect:/";
    }

    @DeleteMapping("/delete")
    public String deleteTourist(@RequestParam Integer id)
    {
        String url = BASE_URL + "/deleteTourist/"+id;
//        restTemplate.delete(url);
        webClient.delete()
                .uri("/deleteTourist/"+id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
        return "redirect:/";
    }

    @PatchMapping("/update-budget")
    public String updateBudget(@RequestParam("id") Integer id, @RequestParam("budget") Double budget)
    {
        String url = BASE_URL + "/updateBudget/"+ id + "/" + budget;
        // String response = restTemplate.patchForObject(url, null, String.class, id , budget);
//        restTemplate.exchange(url, HttpMethod.PATCH, null, String.class);

        webClient.patch().uri("/updateBudget/{id}/{budget}",id , budget)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return "redirect:/";
    }

}
