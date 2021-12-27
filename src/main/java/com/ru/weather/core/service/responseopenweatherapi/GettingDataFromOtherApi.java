package com.ru.weather.core.service.responseopenweatherapi;

import com.ru.weather.core.model.openweatherapi.other.AllData;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GettingDataFromOtherApi {
    static final String API_KEY_OPENWEATHER = "c7bcf64e62b56fb2bbeb904141a0c9ee";
    private final RestTemplate restTemplate = new RestTemplate();

    //Получение Json-а из чужого апи по заданным параметрам
    public AllData getJsonFromOtherApi(Double lat, Double lon) {
        return restTemplate.getForEntity(buildUrl(lat, lon).toString(), AllData.class).getBody();
    }

    public AllData getJsonFromOtherApi(Double lat, Double lon, Long unixTime) {
        return restTemplate.getForEntity(buildUrl(lat, lon, unixTime).toString(), AllData.class).getBody();
    }

    //Билдер ссылки(запроса) в жучое апи
    public UriComponents buildUrl(Double lat, Double lon) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host("api.openweathermap.org")
                .path("/data/2.5/onecall")
                .queryParam("appid", API_KEY_OPENWEATHER)
                .queryParam("exclude", "hourly")
                .queryParam("units", "metric")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("lang", "ru")
                .buildAndExpand();
        return uriComponents;
    }

    //Билдер ссылки со временем
    public UriComponents buildUrl(Double lat, Double lon, Long unixTime) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host("api.openweathermap.org")
                .path("/data/2.5/onecall")
                .queryParam("appid", API_KEY_OPENWEATHER)
                .queryParam("exclude", "hourly")
                .queryParam("units", "metric")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("lang", "ru")
                .queryParam("dt", unixTime)
                .buildAndExpand();
        return uriComponents;
    }
}
