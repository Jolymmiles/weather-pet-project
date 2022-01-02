package com.ru.weather.api.controller.controller;


import com.ru.weather.api.controller.mapper.Mapper;
import com.ru.weather.core.dto.WeatherDto;
import com.ru.weather.core.service.weather.WeatherService;
import com.ru.weather.db.entity.weather.WeatherEntity;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
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
    @RequestMapping(value = "/{id}/today", method = RequestMethod.GET)
    @GetMapping
    @ResponseBody
    public WeatherDto getWeather(@PathVariable Long id) {
        logger.info("Обращение к /{}/today", id);
        WeatherEntity weatherEntity = weatherService.getWeatherByNow(id);
        return mapper.map(weatherEntity, WeatherDto.class);
    }

    @ApiOperation(value = "Получение погоды на неделю по Id города")
    @RequestMapping(value = "/{id}/weekly", method = RequestMethod.GET)
    @GetMapping
    @ResponseBody
    public List<WeatherDto> getWeeklyWeather(@PathVariable Long id) {
        logger.info("Образение к /{}/weekly", id);
        return mapper.mapAsList(weatherService.getWeeklyWeather(id), WeatherDto.class);
    }

    @ApiOperation(value = "Получение всей кэшированное погоды по Id города, стандартный метод возвращает всю кэшированную погоду, фильтр оп наименованию")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @GetMapping
    @ResponseBody
    public List<WeatherDto> getAllWeather(@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "letters", required = false) String letters) {
        logger.info("обращение к /weather?id={}&letters={}", id, letters);
        if (id == null) {
            return mapper.mapAsList(weatherService.getAllWeather(), WeatherDto.class);
        } else if (!letters.isEmpty()) {
            return mapper.mapAsList(weatherService.getAllWeatherWithContainsLetter(letters), WeatherDto.class);
        } else {
            return mapper.mapAsList(weatherService.getAllCachedWeatherForThisCity(id), WeatherDto.class);
        }
    }

    @GetMapping
    @ResponseBody
    @RequestMapping(value =  "/today/excel/{id}")
    public ResponseEntity<InputStreamResource> getTodayWeatherExcel(@PathVariable Long id) throws FileNotFoundException {
        logger.info("Обращение к /today/excel/{}", id);
        return weatherService.getExcelFileForWeatherToday(id);

    }

    @GetMapping
    @ResponseBody
    @RequestMapping(value =  "/weekly/excel/{id}")
    public ResponseEntity<InputStreamResource> getWeeklyWeatherExcel(@PathVariable Long id) throws FileNotFoundException {
        logger.info("Обращение к /weekly/excel/{}", id);
        return weatherService.getExcelWeeklyWeather(id);
    }

    //Стандартные запросы удаления, добавления, создания по id
    @ApiOperation(value = "Удаление погоды по Id")
    @RequestMapping(value = "/{id}/remove", method = RequestMethod.DELETE)
    @DeleteMapping
    public void removeWeather(@PathVariable Long id) {
        logger.info("Удаление погоды по id /{}/remove", id);
        weatherService.removeWeatherById(id);
    }

    @ApiOperation(value = "Обновление погоды по Id")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.PUT)
    @PutMapping
    public WeatherDto updateWeatherById(@PathVariable Long id, @RequestBody WeatherDto weatherDto) {
        logger.info("Обновление погоды по id /{}/remove", id);
        WeatherEntity weatherToService = mapper.map(weatherDto, WeatherEntity.class);
        return mapper.map(weatherService.updateWeatherById(id, weatherToService), WeatherDto.class);
    }

    @ApiOperation(value = "Добавление погоды")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PostMapping
    public WeatherDto addWeather(@RequestBody WeatherDto weatherDto) {
        logger.info("Добавление погоды /add");
        return mapper.map(weatherService.addWeather(weatherDto), WeatherDto.class);
    }

    @ApiOperation(value = "Получение погоды по Id")
    @RequestMapping(value = "/{id}/get", method = RequestMethod.GET)
    @GetMapping
    public WeatherDto getWeatherById(@PathVariable Long id) {
        logger.info("Получение погоды по id /{}/remove", id);
        return mapper.map(weatherService.getWeatherById(id), WeatherDto.class);
    }


}
