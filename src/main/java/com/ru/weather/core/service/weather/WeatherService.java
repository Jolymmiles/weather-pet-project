package com.ru.weather.core.service.weather;

import com.ru.weather.core.dto.WeatherDto;
import com.ru.weather.db.entity.city.CityEntity;
import com.ru.weather.db.entity.weather.WeatherEntity;

import java.time.LocalDate;

public interface WeatherService {

    /**
     * Getting weather data by date and city
     *
     * @param cityEntity city of weather
     * @param date       date of weather
     * @return WeatherEntity
     */
    WeatherEntity getWeatherByDate(CityEntity cityEntity, LocalDate date);

    /**
     * Removing weather by id
     *
     * @param id weather id
     */
    void removeWeatherById(Long id);

    /**
     * Adding weather
     *
     * @param weatherDto weather data
     * @return WeatherEntity
     */
    WeatherEntity addWeather(WeatherDto weatherDto);

    /**
     * Updating weather by data with id
     *
     * @param weatherDto weather data
     * @return WeatherEntity
     */
    WeatherEntity updateWeatherById(WeatherDto weatherDto);

    /**
     * Getting weather by id
     *
     * @param id weather id
     * @return WeatherEntity
     */
    WeatherEntity getWeatherById(Long id);
}
