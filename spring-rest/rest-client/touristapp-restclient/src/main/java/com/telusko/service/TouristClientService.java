package com.telusko.service;

import com.telusko.dto.Tourist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TouristClientService {
    @Autowired
    private RestTemplate restTemplate;

    private final String BASE_URL = "http://localhost:8080/tourist";

    public String registerTourist(Tourist tourist)
    {
        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/register", tourist, String.class);
        return response.getBody();
    }

    public Tourist getTourist(Integer id)
    {
        return restTemplate.getForObject(BASE_URL + "/getTourist/" + id, Tourist.class);
    }

    public List<Tourist> getAllTourists()
    {
        // option 1
//        ResponseEntity<List> response = restTemplate.getForEntity(BASE_URL + "/getAllTourists", List.class);
//        return response.getBody();

        // option 2
        ResponseEntity<List<Tourist>> response = restTemplate.exchange(
                BASE_URL + "/getAllTourists",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Tourist>>() {
                }
        );

        return response.getBody();

    }

    public String updateTourist(Tourist tourist)
    {
        restTemplate.put(BASE_URL + "/updateTourist", tourist);
        return "Update Successfully from Client App";

    }

    public String deleteTourist(Integer id)
    {
//        restTemplate.delete(BASE_URL + "/deleteTourist/" + id);
//        return "Deleted Successfully from Client App";
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/deleteTourist/" + id, HttpMethod.DELETE, null, String.class);
        return response.getBody();
    }

    public String updateBudget(Integer id, Double budget)
    {
        restTemplate.patchForObject(BASE_URL + "/updateBudget/" + id + "/" + budget, null, String.class);
        return "Update Successfully from Client App";
    }

}
