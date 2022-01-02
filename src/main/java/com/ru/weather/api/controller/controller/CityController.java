package com.ru.weather.api.controller.controller;

import com.ru.weather.api.controller.mapper.Mapper;
import com.ru.weather.core.dto.CityDto;
import com.ru.weather.core.service.city.CityService;
import com.ru.weather.db.entity.city.CityEntity;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/city")
public class CityController {
    Logger logger = LoggerFactory.getLogger("Логгер контролера городов");

    @Autowired
    private Mapper mapper;

    @Autowired
    private CityService cityService;

    //Стандартные запросы удаления, добавления, создания по id
    @ApiOperation(value = "Удаление города по Id")
    @RequestMapping(value = "/{id}/remove", method = RequestMethod.DELETE)
    @DeleteMapping
    public void removeWeather(@PathVariable Long id) {
        logger.info("Обращение к /{}/remove", id);
        cityService.removeCityById(id);
    }

    @ApiOperation(value = "Обновление города по Id")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.PUT)
    @PutMapping
    public CityDto updateCityById(@PathVariable Long id, @RequestBody CityDto cityDto) {
        logger.info("Обращение к /{}/update", id);
        CityEntity cityEntityToController = mapper.map(cityDto, CityEntity.class);
        return mapper.map(cityService.updateCityById(id, cityEntityToController), CityDto.class);
    }

    @ApiOperation(value = "Добавление города")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PostMapping
    public CityDto addCity(@RequestBody CityDto cityDto) {
        logger.info("Обращение к /add");
        return mapper.map(cityService.addCity(cityDto), CityDto.class);
    }

    @ApiOperation(value = "Получение города по Id")
    @RequestMapping(value = "/{id}/get", method = RequestMethod.GET)
    @GetMapping
    @ResponseBody
    public CityDto getCityById(@PathVariable Long id) {
        logger.info("Обращение к /{}/get", id);
        return mapper.map(cityService.getCityById(id), CityDto.class);
    }


    @ApiOperation(value = "Получение всех городов, включающих буквы или же без фильтра по наименованию")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @GetMapping
    @ResponseBody
    public List<CityDto> getAllCity(@RequestParam(value = "letters", required = false) String letters) {
        logger.info("Обращение к /city?letters={}", letters);
        if (letters == null) {
            return mapper.mapAsList(cityService.getAllCity(), CityDto.class);
        } else {
            return mapper.mapAsList(cityService.getCityWithThisLetters(letters), CityDto.class);
        }

    }

}
