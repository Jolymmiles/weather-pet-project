package com.ru.weather.core.service.weather;

import com.ru.weather.db.entity.weather.WeatherEntity;
import com.sun.istack.NotNull;
import com.sun.media.sound.InvalidDataException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface WeatherService {

    /**
     * Getting weather data by date and city
     *
     * @param id city id of weather
     * @return List with WeatherEntity
     */
    List<WeatherEntity> getWeatherByNow(@NotNull Long id);

    /**
     * Getting weekly weather
     *
     * @param id city id of weather
     * @return List WeatherEntity
     */
    List<WeatherEntity> getWeeklyWeather(@NotNull Long id);


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
    List<WeatherEntity> getAllWeather();

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
    WeatherEntity addWeather(@NotNull WeatherEntity weatherEntity) throws InvalidDataException;

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

    /**
     * Getting Excel File
     *
     * @param id city id
     * @param type of weather
     *
     */
    ResponseEntity<InputStreamResource> getExcelFileOfWeatherWithType(@NotNull Long id, @NotNull String type) throws IOException;


}
