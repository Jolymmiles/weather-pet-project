package com.ru.weather.api.controller.controller;


import com.ru.weather.api.controller.mapper.Mapper;
import com.ru.weather.core.dto.CityDto;
import com.ru.weather.core.dto.WeatherDto;
import com.ru.weather.core.service.city.CityService;
import com.ru.weather.core.service.weather.WeatherService;
import com.ru.weather.db.entity.weather.WeatherEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private Mapper mapper;

    @Autowired
    WeatherService weatherService;

    @Autowired
    CityService cityService;


    @RequestMapping(value = "/get/onday")
    @GetMapping
    public @ResponseBody
    WeatherDto getWeather(@RequestParam String city, @RequestParam(required = false) Date date) {
        CityDto cityDto = mapper.map(cityService.getByCityname(city), CityDto.class);

        WeatherEntity weatherEntity = weatherService.getWeatherByDate(cityDto.getId(), date);
        return mapper.map(weatherEntity, WeatherDto.class);

    }

}
