package com.ru.weather.api.controller.controller;

import com.ru.weather.api.controller.mapper.Mapper;
import com.ru.weather.core.dto.CityDto;
import com.ru.weather.core.service.city.CityService;
import com.ru.weather.db.entity.city.CityEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/city/{id}")
public class CityController {
    Logger logger = LoggerFactory.getLogger("Логгер контролера городов");

    @Autowired
    private Mapper mapper;

    @Autowired
    private CityService cityService;

    @ApiOperation(value = "Удаление городы по Id")
    @DeleteMapping("/{id}/remove")
    public void removeWeather(@PathVariable Long id) {
        logger.info("Обращение к /{}/remove", id);
        cityService.removeCityById(id);
    }

    @ApiOperation(value = "Обновление города по Id")
    @PutMapping("/{id}/update")
    public CityDto updateCityById(@ApiParam(value = "Id города", required = true) @PathVariable Long id, @RequestBody CityDto cityDto) {
        logger.info("Обращение к /{}/update", id);
        return mapper.map(cityService.updateCityById(id, mapper.map(cityDto, CityEntity.class)), CityDto.class);
    }

    @ApiOperation(value = "Добавление города")
    @PostMapping("/add")
    public ResponseEntity<CityDto> addCity(@RequestBody CityDto cityDto) {
        logger.info("Обращение к /add");
        try {
            return new ResponseEntity<CityDto>(mapper.map(cityService.addCity(mapper.map(cityDto, CityEntity.class)), CityDto.class), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Получение города по Id")
    @GetMapping("/get")
    public CityDto getCityById(@ApiParam(value = "Id города", required = true) @PathVariable Long id) {
        logger.info("Обращение к /{}/get", id);
        return mapper.map(cityService.getCityById(id), CityDto.class);
    }


    @ApiOperation(value = "Получение всех городов, включающих буквы или же без фильтра по наименованию")
    @GetMapping("")
    public List<CityDto> getAllCity(@ApiParam(value = "Фильтр по наименованию") @RequestParam(value = "cityNameLike", required = false) String cityNameLike) {
        logger.info("Обращение к /city?cityNameLike={}", cityNameLike);
        return mapper.mapAsList(cityService.getCityWithThisLetters(cityNameLike), CityDto.class);

    }

}
