package com.ru.weather.core.logical;

import com.ru.weather.core.model.openweatherapi.other.AllData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class GettingDataFromOtherApi {
    static final String API_KEY_OPENWEATHER = "c7bcf64e62b56fb2bbeb904141a0c9ee";
    private final RestTemplate restTemplate = new RestTemplate();

    //Получение Json-а из чужого апи по заданным параметрам
    public ResponseEntity<AllData> getJsonFromOtherApi() {
        return restTemplate.getForEntity(buildUrl().toString(), AllData.class);
    }

    //Билдер ссылки(запроса) в жучое апи
    public UriComponents buildUrl() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host("api.openweathermap.org")
                .path("/data/2.5/onecall")
                .queryParam("appid", API_KEY_OPENWEATHER)
                .queryParam("exclude", "hourly")
                .queryParam("units", "metric")
                .queryParam("lat", "46.39")
                .queryParam("lon", "-47.91").buildAndExpand();
        return uriComponents;


    }
}
