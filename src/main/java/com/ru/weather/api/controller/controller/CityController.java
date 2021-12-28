package com.ru.weather.api.controller.controller;

import com.ru.weather.api.controller.mapper.Mapper;
import com.ru.weather.core.dto.CityDto;
import com.ru.weather.core.service.city.CityService;
import com.ru.weather.db.entity.city.CityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/city")
public class CityController {

    @Autowired
    private Mapper mapper;

    @Autowired
    private CityService cityService;

    //Стандартные запросы удаления, добавления, создания по id
    @RequestMapping(value = "/{id}/remove")
    @DeleteMapping
    public void removeWeather(@PathVariable Long id) {
        cityService.removeCityById(id);
    }

    @RequestMapping(value = "/{id}/update")
    @PutMapping
    public CityDto updateCityById(@PathVariable Long id, @RequestBody CityDto cityDto) {
        CityEntity cityEntityToController = mapper.map(cityDto, CityEntity.class);
        return mapper.map(cityService.updateCityById(id, cityEntityToController), CityDto.class);
    }

    @RequestMapping(value = "/add")
    @PostMapping
    public CityDto addCity(@RequestBody CityDto cityDto) {
        return mapper.map(cityService.addCity(cityDto), CityDto.class);
    }

    @RequestMapping(value = "/{id}/get")
    @GetMapping
    @ResponseBody
    public CityDto getCityById(@PathVariable Long id) {
        return mapper.map(cityService.getCityById(id), CityDto.class);
    }

    @RequestMapping(value = "")
    @GetMapping
    @ResponseBody
    public List<CityDto> getAllCity(@RequestParam(value = "letters", required = false) String letters) {
        if (letters.isEmpty()) {
            return mapper.mapAsList(cityService.getAllCity(), CityDto.class);
        } else {
            return mapper.mapAsList(cityService.getCityWithThisLetters(letters), CityDto.class);
        }

    }

}
