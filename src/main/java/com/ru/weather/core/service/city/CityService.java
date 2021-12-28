package com.ru.weather.core.service.city;

import com.ru.weather.core.dto.CityDto;
import com.ru.weather.db.entity.city.CityEntity;
import com.sun.istack.NotNull;

import java.util.List;

public interface CityService {
    /**
     * Getting city by name
     *
     * @param cityName name of the city
     * @return CityEntity
     */
    CityEntity getByCityName(String cityName);

    /**
     *
     * Getting all city in db
     * @return List of CityEntity
     */
    List<CityEntity> getAllCity();

    /**
     * Getting city
     * @param letters letter
     * @return List of CityEntity
     */
     List<CityEntity> getCityWithThisLetters(String letters);

    /**
     * Removing city by id
     *
     * @param id city id
     */
    void removeCityById(@NotNull Long id);

    /**
     * Adding city
     *
     * @param cityDto city data
     * @return CityEntity
     */
    CityEntity addCity(CityDto cityDto);

    /**
     * Updating city by id
     *
     * @param cityDto city data
     * @return CityEntity
     */
    CityEntity updateCityById(@NotNull Long id, CityEntity cityDto);

    /**
     * Getting city by id
     *
     * @param id city id
     * @return CityEntity
     */
    CityEntity getCityById(@NotNull Long id);


}
