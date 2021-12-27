package com.ru.weather.api.controller.controller;


import com.ru.weather.api.controller.mapper.Mapper;
import com.ru.weather.core.dto.WeatherDto;
import com.ru.weather.core.service.city.CityService;
import com.ru.weather.core.service.weather.WeatherService;
import com.ru.weather.db.entity.city.CityEntity;
import com.ru.weather.db.entity.weather.WeatherEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private Mapper mapper;

    @Autowired
    WeatherService weatherService;

    @Autowired
    CityService cityService;


    @RequestMapping(value = "/today")
    @GetMapping
    public @ResponseBody
    WeatherDto getWeather(@RequestParam String city) {
        CityEntity cityEntity = cityService.getByCityName(city);
        WeatherEntity weatherEntity = weatherService.getWeatherByNow(cityEntity);
        return mapper.map(weatherEntity, WeatherDto.class);
    }

    @RequestMapping(value = "/weekly")
    @GetMapping
    public @ResponseBody
    List<WeatherDto> getWeeklyWeather(@RequestParam String city) {
        CityEntity cityEntity = cityService.getByCityName(city);
        return mapper.mapAsList(weatherService.getWeeklyWeather(cityEntity), WeatherDto.class);
    }

    @RequestMapping(value = "/all")
    @GetMapping
    public @ResponseBody
    List<WeatherDto> getAllWeatherForCity(@RequestParam String city) {
        CityEntity cityEntity = cityService.getByCityName(city);
        List<WeatherEntity> weatherEntities = weatherService.getAllCachedWeatherForThisCity(cityEntity);
        return mapper.mapAsList(weatherEntities, WeatherDto.class);
    }

    //Стандартные запросы удаления, добавления, создания по id
    @RequestMapping(value = "/remove")
    @DeleteMapping
    public void removeWeather(@RequestParam Long id) {
        weatherService.removeWeatherById(id);
    }

    @RequestMapping(value = "/update")
    @PutMapping
    public WeatherDto updateWeatherById(@RequestBody WeatherDto weatherDto) {
        return mapper.map(weatherService.updateWeatherById(weatherDto), WeatherDto.class);
    }

    @RequestMapping(value = "/add")
    @PostMapping
    public WeatherDto addWeather(@RequestBody WeatherDto weatherDto) {
        return mapper.map(weatherService.addWeather(weatherDto), WeatherDto.class);
    }

    @RequestMapping(value = "/get")
    @GetMapping
    public WeatherDto getWeatherById(@RequestParam Long id) {
        return mapper.map(weatherService.getWeatherById(id), WeatherDto.class);
    }


}
