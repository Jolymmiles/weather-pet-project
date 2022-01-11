package com.ru.weather.api.controller.controller;


import com.ru.weather.api.controller.mapper.Mapper;
import com.ru.weather.core.dto.WeatherDto;
import com.ru.weather.core.service.weather.WeatherService;
import com.ru.weather.db.entity.weather.WeatherEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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
        logger.info("Обращение к /{}/today", id);
        WeatherEntity weatherEntity = weatherService.getWeatherByNow(id);
        return mapper.map(weatherEntity, WeatherDto.class);
    }

    @ApiOperation(value = "Получение погоды на неделю по Id города")
    @GetMapping("/{id}/weekly")
    public List<WeatherDto> getWeeklyWeather(@ApiParam(value = "Id города", required = true) @PathVariable Long id) {
        logger.info("Образение к /{}/weekly", id);
        return mapper.mapAsList(weatherService.getWeeklyWeather(id), WeatherDto.class);
    }

    @ApiOperation(value = "Получение всей кэшированное погоды по Id города, стандартный метод возвращает всю кэшированную погоду, фильтр по наименованию")
    @GetMapping("")
    public List<WeatherDto> getAllWeather(@ApiParam(value = "Id города", required = true) @RequestParam(value = "id") Long id,
                                          @ApiParam(value = "Фильтр по наименованию") @RequestParam(value = "cityNameLike", required = false) String cityNameLike) {
        logger.info("обращение к /?id={}&cityNameLike={}", id, cityNameLike);
        if (cityNameLike == null) {
            return mapper.mapAsList(weatherService.getAllCachedWeatherForThisCity(id), WeatherDto.class);
        }
        return mapper.mapAsList(weatherService.getAllWeatherWithContainsLetter(cityNameLike), WeatherDto.class);

    }


    @ApiOperation(value = "Получение Excel файла погоды на день")
    @GetMapping("/today/excel/{id}")
    public ResponseEntity<InputStreamResource> getTodayWeatherExcel(@ApiParam(value = "Id города", required = true) @PathVariable Long id) throws IOException {
        logger.info("Обращение к /today/excel/{}", id);
        return weatherService.getExcelFileForWeatherToday(id);
    }


    @ApiOperation(value = "Получение Excel файла погоды на неделю")
    @GetMapping("/weekly/excel/{id}")
    public ResponseEntity<InputStreamResource> getWeeklyWeatherExcel(@ApiParam(value = "Id города", required = true) @PathVariable Long id) throws IOException {
        logger.info("Обращение к /weekly/excel/{}", id);
        return weatherService.getExcelWeeklyWeather(id);
    }


    @ApiOperation(value = "Удаление погоды по Id")
    @DeleteMapping("/{id}/remove")
    public void removeWeather(@ApiParam(value = "Id погоды", required = true) @PathVariable Long id) {
        logger.info("Удаление погоды по id /{}/remove", id);
        weatherService.removeWeatherById(id);
    }

    @ApiOperation(value = "Обновление погоды по Id")
    @PutMapping("/{id}/update")
    public WeatherDto updateWeatherById(@ApiParam(value = "Id погоды", required = true) @PathVariable Long id, @RequestBody WeatherDto weatherDto) {
        logger.info("Обновление погоды по id /{}/remove", id);
        return mapper.map(weatherService.updateWeatherById(id, mapper.map(weatherDto, WeatherEntity.class)), WeatherDto.class);
    }

    @ApiOperation(value = "Добавление погоды")
    @PostMapping("/add")
    public WeatherDto addWeather(@RequestBody WeatherDto weatherDto) {
        logger.info("Добавление погоды /add");
        return mapper.map(weatherService.addWeather(mapper.map(weatherDto, WeatherEntity.class)), WeatherDto.class);
    }

    @ApiOperation(value = "Получение погоды по Id")
    @GetMapping("/{id}/get")
    public WeatherDto getWeatherById(@ApiParam(value = "Id погоды", required = true) @PathVariable Long id) {
        logger.info("Получение погоды по id /{}/remove", id);
        return mapper.map(weatherService.getWeatherById(id), WeatherDto.class);
    }


}
