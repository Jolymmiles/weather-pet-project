package com.ru.weather.api.controller.controller;


import com.ru.weather.api.controller.mapper.Mapper;
import com.ru.weather.core.dto.WeatherDto;
import com.ru.weather.core.service.weather.WeatherService;
import com.ru.weather.db.entity.weather.WeatherEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public WeatherDto getWeather(@ApiParam(value = "Id города", required = true) @PathVariable Long id) {
        logger.info("Обращение к /cities/{}/today", id);
        return mapper.map(weatherService.getWeatherByNow(id), WeatherDto.class);
    }

    @ApiOperation(value = "Получение погоды на неделю по Id города")
    @GetMapping("/cities/{id}/weekly")
    public List<WeatherDto> getWeeklyWeather(@ApiParam(value = "Id города", required = true) @PathVariable Long id) {
        logger.info("Обращение к /cities/{}/weekly", id);
        return mapper.mapAsList(weatherService.getWeeklyWeather(id), WeatherDto.class);
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
        if (type.equals("today")) {
            logger.info("Обращение к /cities/{}/excel?type={}", id, type);
            return weatherService.getExcelFileForWeatherToday(id);
        } else if (type.equals("weekly")) {
            logger.info("Обращение к /cities/{}/excel?type={}", id, type);
            return weatherService.getExcelWeeklyWeather(id);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @ApiOperation(value = "Удаление погоды по Id")
    @DeleteMapping("/{id}/remove")
    public void removeWeather(@ApiParam(value = "Id погоды", required = true) @PathVariable Long id) {
        logger.info("Удаление погоды по id /{}/remove", id);
        weatherService.removeWeatherById(id);
    }

    @ApiOperation(value = "Обновление погоды по Id")
    @PutMapping("/{id}/update")
    public WeatherDto updateWeatherById(@ApiParam(value = "Id погоды", required = true)@PathVariable Long id, @RequestBody WeatherDto weatherDto) {
        logger.info("Обновление погоды по id /{}/update", id);
        return mapper.map(weatherService.updateWeatherById(id, mapper.map(weatherDto, WeatherEntity.class)), WeatherDto.class);
    }

    @ApiOperation(value = "Добавление погоды")
    @PostMapping("/add")
    public WeatherDto addWeather(@RequestBody WeatherDto weatherDto) {
        logger.info("Добавление погоды /add, Данные:{}", weatherDto.toString());
        return mapper.map(weatherService.addWeather(mapper.map(weatherDto, WeatherEntity.class)), WeatherDto.class);
    }

    @ApiOperation(value = "Получение погоды по Id")
    @GetMapping("/{id}/get")
    public WeatherDto getWeatherById(@ApiParam(value = "Id погоды", required = true) @PathVariable Long id) {
        logger.info("Получение погоды по id /{}/get", id);
        return mapper.map(weatherService.getWeatherById(id), WeatherDto.class);
    }


}
