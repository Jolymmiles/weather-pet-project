package com.ru.weather.api.controller.controller;



import com.ru.weather.api.controller.mapper.Mapper;
import com.ru.weather.core.service.dto.WeatherDto;
import com.ru.weather.db.entity.weather.WeatherEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private Mapper mapper;


    @RequestMapping(value = "/get/{city}")
    @GetMapping
    public @ResponseBody WeatherDto getWeather(@RequestParam("city") String city){
        //Пока что так заглушка
        return mapper.map(WeatherEntity.class, WeatherDto.class);

    }

}
