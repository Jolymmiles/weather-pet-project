package com.ru.weather.core.service.weather;

import com.ru.weather.core.dto.WeatherDto;
import com.ru.weather.db.entity.weather.WeatherEntity;
import com.sun.istack.NotNull;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface WeatherService {

    /**
     * Getting weather data by date and city
     *
     * @param id city id of weather
     * @return WeatherEntity
     */
    WeatherEntity getWeatherByNow(@NotNull Long id);

    /**
     * Getting weekly weather
     *
     * @param id city id of weather
     * @return List WeatherEntity
     */
    List<WeatherEntity> getWeeklyWeather(@NotNull Long id);

    /**
     * Getting all weather with included letters
     *
     * @param letters letter for search
     * @return List WeatherEntity
     */
    public List<WeatherEntity> getAllWeatherWithContainsLetter(String letters);

    /**
     * Get excel file for today weather
     *
     * @param id id
     * @return link
     */
    ResponseEntity<InputStreamResource> getExcelFileForWeatherToday(Long id) throws IOException;

    /**
     * Getting excel for weekly weather
     *
     * @param id city id
     * @return excel
     * @throws FileNotFoundException
     */
    ResponseEntity<InputStreamResource> getExcelWeeklyWeather(Long id) throws IOException;

    /**
     * Getting all weather to the city
     *
     * @param id city id of weather
     * @return List WeatherEntity
     */
    List<WeatherEntity> getAllCachedWeatherForThisCity(@NotNull Long id);

    /**
     * @return List of WeatherEntity
     */
    public List<WeatherEntity> getAllWeather();

    /**
     * Removing weather by id
     *
     * @param id weather id
     */
    void removeWeatherById(@NotNull Long id);

    /**
     * Adding weather
     *
     * @param weatherEntity weather data
     * @return WeatherEntity
     */
    WeatherEntity addWeather(WeatherEntity weatherEntity);

    /**
     * Updating weather by data with id
     *
     * @param weatherEntity weather data
     * @return WeatherEntity
     */
    WeatherEntity updateWeatherById(@NotNull Long id, WeatherEntity weatherEntity);

    /**
     * Getting weather by id
     *
     * @param id weather id
     * @return WeatherEntity
     */
    WeatherEntity getWeatherById(@NotNull Long id);


}
