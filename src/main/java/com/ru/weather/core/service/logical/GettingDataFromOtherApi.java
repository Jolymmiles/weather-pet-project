package com.ru.weather.core.service.logical;

import com.ru.weather.core.model.openweatherapi.AllData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GettingDataFromOtherApi {
    final String API_KEY_OPENWEATHER = "c7bcf64e62b56fb2bbeb904141a0c9ee";
    private String url = "https://api.openweathermap.org/data/2.5/onecall?";

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<AllData> responseEntity = restTemplate.exchange("");

    public String makeUrl(Double lat, Double lon) {

    }
}
