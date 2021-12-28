package com.ru.weather.api.controller.controller;


import com.ru.weather.api.controller.mapper.Mapper;
import com.ru.weather.core.dto.WeatherDto;
import com.ru.weather.core.service.weather.WeatherService;
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


    @RequestMapping(value = "/{id}/today")
    @GetMapping
    @ResponseBody
    public
    WeatherDto getWeather(@PathVariable Long id) {
        WeatherEntity weatherEntity = weatherService.getWeatherByNow(id);
        return mapper.map(weatherEntity, WeatherDto.class);
    }

    @RequestMapping(value = "/{id}/weekly")
    @GetMapping
    @ResponseBody
    public
    List<WeatherDto> getWeeklyWeather(@PathVariable Long id) {
        return mapper.mapAsList(weatherService.getWeeklyWeather(id), WeatherDto.class);
    }

    @RequestMapping(value = "/{id}/all_weather_by_city_id")
    @GetMapping
    @ResponseBody
    public
    List<WeatherDto> getAllWeatherForCity(@PathVariable Long id) {
        List<WeatherEntity> weatherEntities = weatherService.getAllCachedWeatherForThisCity(id);
        return mapper.mapAsList(weatherEntities, WeatherDto.class);
    }

    @RequestMapping(value = "")
    @GetMapping
    @ResponseBody
    public List<WeatherDto> getAllWeather(@RequestParam(value = "id", required = false) Long id) {
        if (id == null) {
            return mapper.mapAsList(weatherService.getAllWeather(), WeatherDto.class);
        } else {
            return mapper.mapAsList(weatherService.getAllCachedWeatherForThisCity(id), WeatherDto.class);
        }

    }

    //Стандартные запросы удаления, добавления, создания по id
    @RequestMapping(value = "/{id}/remove")
    @DeleteMapping
    public void removeWeather(@PathVariable Long id) {
        weatherService.removeWeatherById(id);
    }

    @RequestMapping(value = "/{id}/update")
    @PutMapping
    public WeatherDto updateWeatherById(@PathVariable Long id, @RequestBody WeatherDto weatherDto) {
        WeatherEntity weatherToService = mapper.map(weatherDto, WeatherEntity.class);
        return mapper.map(weatherService.updateWeatherById(id, weatherToService), WeatherDto.class);
    }

    @RequestMapping(value = "/add")
    @PostMapping
    public WeatherDto addWeather(@RequestBody WeatherDto weatherDto) {
        return mapper.map(weatherService.addWeather(weatherDto), WeatherDto.class);
    }

    @RequestMapping(value = "/{id}/get")
    @GetMapping
    public WeatherDto getWeatherById(@PathVariable Long id) {
        return mapper.map(weatherService.getWeatherById(id), WeatherDto.class);
    }


}
