package com.ru.weather.api.controller.controller;

import com.ru.weather.api.controller.mapper.Mapper;
import com.ru.weather.core.dto.CityDto;
import com.ru.weather.core.service.city.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/city")
public class CityController {

    @Autowired
    private Mapper mapper;

    @Autowired
    private CityService cityService;

    //Стандартные запросы удаления, добавления, создания по id
    @RequestMapping(value = "/remove")
    @DeleteMapping
    public void removeWeather(@RequestParam Long id) {
        cityService.removeCityById(id);
    }

    @RequestMapping(value = "/update")
    @PutMapping
    public CityDto updateCityById(@RequestBody CityDto cityDto) {
        return mapper.map(cityService.updateCityById(cityDto), CityDto.class);
    }

    @RequestMapping(value = "/add")
    @PostMapping
    public CityDto addCity(@RequestBody CityDto cityDto) {
        return mapper.map(cityService.addCity(cityDto), CityDto.class);
    }

    @RequestMapping(value = "/get")
    @GetMapping
    public CityDto getCityById(@RequestParam Long id) {
        return mapper.map(cityService.getCityById(id), CityDto.class);
    }
}
