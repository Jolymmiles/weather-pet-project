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
import java.util.List;


@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    Logger logger = LoggerFactory.getLogger("Логгер контролера погоды");

    @Autowired
    private Mapper mapper;

    @Autowired
    private WeatherService weatherService;

    @ApiOperation(value = "Получение погоды")
    @GetMapping("/cities/{id}")
    public ResponseEntity<List<WeatherDto>> getWeather(@ApiParam(value = "тип на день(today) или неделю(weekly)", required = true) @RequestParam(value = "type") String type,
                                                       @ApiParam(value = "Id города", required = true) @PathVariable Long id) {
        logger.info("Обращение к /cities/{}?type={}", id, type);
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (type.equals("today")) {
            return new ResponseEntity<>(mapper.mapAsList(weatherService.getWeatherByNow(id), WeatherDto.class), HttpStatus.OK);
        } else if (type.equals("weekly")) {
            return new ResponseEntity<>(mapper.mapAsList(weatherService.getWeeklyWeather(id), WeatherDto.class), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(value = "Получение всей кэшированное погоды, стандартный метод возвращает всю кэшированную погоду, фильтр по id")
    @GetMapping("/all")
    public List<WeatherDto> getAllWeather(@ApiParam(value = "Id города") @RequestParam(value = "city-id", required = false) Long id) {
        logger.info("обращение к /all/?id={}", id);
        if (id == null) {
            logger.info("Параметры отсутствуют: {}", id);
            return mapper.mapAsList(weatherService.getAllWeather(), WeatherDto.class);
        }
        logger.info("Стандартный метод: {}", id);
        return mapper.mapAsList(weatherService.getAllCachedWeatherForThisCity(id), WeatherDto.class);

    }


    @ApiOperation(value = "Получение Excel файла погоды. Возможные параметры: weekly, today")
    @GetMapping("/cities/{id}/excel")
    public ResponseEntity<InputStreamResource> getWeatherExcel(@ApiParam(value = "type", required = true) @RequestParam(value = "type") String type,
                                                               @PathVariable Long id) {
        if (id == null | type == null) {
            logger.info("Отсутствует один из параметров. {}, {}", id, type);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            if (type.equals("today")) {
                logger.info("Обращение к /cities/{}/excel?type={}. Успешно", id, type);
                return weatherService.getExcelFileOfWeatherWithType(id, type);
            } else if (type.equals("weekly")) {
                logger.info("Обращение к /cities/{}/excel?type={}. Успешно", id, type);
                return weatherService.getExcelFileOfWeatherWithType(id, type);
            }
            logger.info("Неверный параметр: {}, {}", id, type);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.info("Неверный запрос");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(value = "Удаление погоды по Id")
    @DeleteMapping("/{id}/remove")
    public ResponseEntity removeWeather(@ApiParam(value = "Id погоды", required = true) @PathVariable Long id) {
        logger.info("Удаление погоды по id /{}/remove", id);
        if (id == null) {
            logger.info("Отсутствует ID: {}", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            logger.info("Сущность успешно удалена");
            weatherService.removeWeatherById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            logger.info("Сущность не найдена. Id: {}", id);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


    @ApiOperation(value = "Обновление погоды по Id")
    @PutMapping("/{id}/update")
    public ResponseEntity<WeatherDto> updateWeatherById(@ApiParam(value = "Id погоды", required = true) @PathVariable Long id, @RequestBody WeatherDto weatherDto) {
        logger.info("Обновление погоды по id /{}/update", id);
        if (id == null) {
            logger.info("Отсутствует ID: {}", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            logger.info("Успешно обновлена сущность. Данные: {}", weatherDto);
            return new ResponseEntity<>(mapper.map(weatherService.updateWeatherById(id, mapper.map(weatherDto, WeatherEntity.class)), WeatherDto.class), HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Сущность не найдена: {}", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(value = "Добавление погоды")
    @PostMapping("/add")
    public ResponseEntity<WeatherDto> addWeather(@RequestBody WeatherDto weatherDto) {
        logger.info("Добавление погоды /add, Данные:{}", weatherDto.toString());
        try {
            logger.info("Сущность успешно добавлена. Данные: {}", weatherDto);
            return new ResponseEntity<>(mapper.map(weatherService.addWeather(mapper.map(weatherDto, WeatherEntity.class)), WeatherDto.class), HttpStatus.OK);
        } catch (InvalidDataException e) {
            logger.info("Сущность уже существует");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


    @ApiOperation(value = "Получение погоды по Id")
    @GetMapping("/{id}/get")
    public ResponseEntity<WeatherDto> getWeatherById(@ApiParam(value = "Id погоды", required = true) @PathVariable Long id) {
        logger.info("Получение погоды по id /{}/get", id);
        try {
            logger.info("Сущность получена");
            return new ResponseEntity<>(mapper.map(weatherService.getWeatherById(id), WeatherDto.class), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            logger.info("Сущность не найдена. Id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
