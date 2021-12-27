package com.ru.weather.core.service.weather;

import com.ru.weather.core.dto.WeatherDto;
import com.ru.weather.db.entity.city.CityEntity;
import com.ru.weather.db.entity.weather.WeatherEntity;

import java.util.List;

public interface WeatherService {

    /**
     * Getting weather data by date and city
     *
     * @param cityEntity city of weather
     * @return WeatherEntity
     */
    WeatherEntity getWeatherByNow(CityEntity cityEntity);

    /**
     * Getting weekly weather
     *
     * @param cityEntity city of weather
     * @return List WeatherEntity
     */
    List<WeatherEntity> getWeeklyWeather(CityEntity cityEntity);

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

    /**
     * Getting all weather to the city
     *
     * @param cityEntity city of weather
     * @return List WeatherEntity
     */
    List<WeatherEntity> getAllCachedWeatherForThisCity(CityEntity cityEntity);
}
