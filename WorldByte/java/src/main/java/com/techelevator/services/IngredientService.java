package com.techelevator.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.Recipe;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class IngredientService { // Switching to Spoonacular

    private String apiURL = "";
    private String appKey = "";

    public List<Recipe> getSearchResults(String searchQuery) {
//        String url = this.apiURL + "&app_id=" + appId + "&app_key=" + appKey + "&q=" + searchQuery;
//
//        HttpEntity<String> httpEntity = new HttpEntity<>("");
//        RestTemplate restTemplate = new RestTemplate();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode;
//
//        // Get request:
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, );


        return null;
    }
}
