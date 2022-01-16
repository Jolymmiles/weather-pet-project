package com.ru.weather.api.controller.controller;


import com.ru.weather.api.controller.mapper.Mapper;
import com.ru.weather.core.dto.WeatherDto;
import com.ru.weather.core.service.weather.WeatherService;
import com.ru.weather.db.entity.weather.WeatherEntity;
import com.sun.media.sound.InvalidDataException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    Logger logger = LoggerFactory.getLogger("Логгер контролера погоды");


    @Autowired
    private Mapper mapper;

    @Autowired
    private WeatherService weatherService;


    @ApiOperation(value = "Получение погода на сегодня по Id города")
    @GetMapping("/cities/{id}/today")
    public ResponseEntity<WeatherDto> getWeather(@ApiParam(value = "Id города", required = true) @PathVariable Long id) {
        logger.info("Обращение к /cities/{}/today", id);
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(mapper.map(weatherService.getWeatherByNow(id), WeatherDto.class), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @ApiOperation(value = "Получение погоды на неделю по Id города")
    @GetMapping("/cities/{id}/weekly")
    public ResponseEntity<List<WeatherDto>> getWeeklyWeather(@ApiParam(value = "Id города", required = true) @PathVariable Long id) {
        logger.info("Обращение к /cities/{}/weekly", id);
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(mapper.mapAsList(weatherService.getWeeklyWeather(id), WeatherDto.class), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @ApiOperation(value = "Получение всей кэшированное погоды, стандартный метод возвращает всю кэшированную погоду, фильтр по id")
    @GetMapping("/all")
    public List<WeatherDto> getAllWeather(@ApiParam(value = "Id города") @RequestParam(value = "city-id", required = false) Long id) {
        logger.info("обращение к /all/?id={}", id);
        if (id == null) {
            return mapper.mapAsList(weatherService.getAllWeather(), WeatherDto.class);
        }
        return mapper.mapAsList(weatherService.getAllCachedWeatherForThisCity(id), WeatherDto.class);

    }


    @ApiOperation(value = "Получение Excel файла погоды. Возможные параметры: weekly, today")
    @GetMapping("/cities/{id}/excel")
    public ResponseEntity<InputStreamResource> getWeatherExcel(@ApiParam(value = "type", required = true) @RequestParam(value = "type") String type,
                                                               @PathVariable Long id) throws IOException {
        if (id == null | type == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            if (type.equals("today")) {
                logger.info("Обращение к /cities/{}/excel?type={}", id, type);
                return weatherService.getExcelFileForWeatherToday(id);
            } else if (type.equals("weekly")) {
                logger.info("Обращение к /cities/{}/excel?type={}", id, type);
                return weatherService.getExcelWeeklyWeather(id);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(value = "Удаление погоды по Id")
    @DeleteMapping("/{id}/remove")
    public ResponseEntity removeWeather(@ApiParam(value = "Id погоды", required = true) @PathVariable Long id) {
        logger.info("Удаление погоды по id /{}/remove", id);
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            weatherService.removeWeatherById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    @ApiOperation(value = "Обновление погоды по Id")
    @PutMapping("/{id}/update")
    public ResponseEntity<WeatherDto> updateWeatherById(@ApiParam(value = "Id погоды", required = true) @PathVariable Long id, @RequestBody WeatherDto weatherDto) {
        logger.info("Обновление погоды по id /{}/update", id);
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(mapper.map(weatherService.updateWeatherById(id, mapper.map(weatherDto, WeatherEntity.class)), WeatherDto.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Добавление погоды")
    @PostMapping("/add")
    public ResponseEntity<WeatherDto> addWeather(@RequestBody WeatherDto weatherDto) {
        logger.info("Добавление погоды /add, Данные:{}", weatherDto.toString());
        try {
            return new ResponseEntity<>(mapper.map(weatherService.addWeather(mapper.map(weatherDto, WeatherEntity.class)), WeatherDto.class), HttpStatus.OK);
        } catch (InvalidDataException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }


    }

    @ApiOperation(value = "Получение погоды по Id")
    @GetMapping("/{id}/get")
    public ResponseEntity<WeatherDto> getWeatherById(@ApiParam(value = "Id погоды", required = true) @PathVariable Long id) {
        logger.info("Получение погоды по id /{}/get", id);
        try {
            return new ResponseEntity<>(mapper.map(weatherService.getWeatherById(id), WeatherDto.class), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
