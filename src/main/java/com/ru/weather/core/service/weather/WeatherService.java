package com.ru.weather.core.service.weather;

import com.ru.weather.db.entity.weather.WeatherEntity;
import com.ru.weather.db.entity.weather.WeatherEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class WeatherService {
    @Autowired
    private WeatherEntityRepository weatherEntityRepository;

    public WeatherEntity getWeatherByDate(Long cityId, Date date){
        WeatherEntity weatherEntity = weatherEntityRepository.findByCityidAndDate(cityId, date);
        return weatherEntity;
    }

}


