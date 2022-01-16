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

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("api/city")
public class CityController {
    Logger logger = LoggerFactory.getLogger("Логгер контролера городов");

    @Autowired
    private Mapper mapper;

    @Autowired
    private CityService cityService;

    @ApiOperation(value = "Удаление города по Id")
    @DeleteMapping("/{id}/remove")
    public ResponseEntity removeWeather(@PathVariable Long id) {
        logger.info("Обращение к /{}/remove", id);
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            cityService.removeCityById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Обновление города по Id")
    @PutMapping("/{id}/update")
    public ResponseEntity<CityDto> updateCityById(@ApiParam(value = "Id города", required = true) @PathVariable Long id, @RequestBody CityDto cityDto) {
        logger.info("Обращение к /{}/update", id);
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(mapper.map(cityService.updateCityById(id, mapper.map(cityDto, CityEntity.class)), CityDto.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Добавление города")
    @PostMapping("/add")
    public ResponseEntity<CityDto> addCity(@RequestBody CityDto cityDto) {
        logger.info("Обращение к /add");
        try {
            return new ResponseEntity<>(mapper.map(cityService.addCity(mapper.map(cityDto, CityEntity.class)), CityDto.class), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Получение города по Id")
    @GetMapping("/{id}/get")
    public ResponseEntity<CityDto> getCityById(@ApiParam(value = "Id города", required = true) @PathVariable Long id) {
        logger.info("Обращение к /{}/get", id);
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(mapper.map(cityService.getCityById(id), CityDto.class), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @ApiOperation(value = "Получение всех городов, включающих буквы или же без фильтра по наименованию")
    @GetMapping("/get-all")
    public ResponseEntity<List<CityDto>> getAllCity(@ApiParam(value = "Фильтр по наименованию") @RequestParam(value = "city-name-like", required = false) String cityNameLike) {
        logger.info("Обращение к /city?cityNameLike={}", cityNameLike);
        return new ResponseEntity<>(mapper.mapAsList(cityService.getCityWithThisLetters(cityNameLike), CityDto.class), HttpStatus.OK);

    }

}
