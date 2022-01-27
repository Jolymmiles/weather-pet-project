package com.ru.weather.core.service.city;

import com.ru.weather.db.entity.city.CityEntity;
import com.sun.istack.NotNull;
import javassist.NotFoundException;

import java.util.List;

public interface CityService {

    /**
     * Getting city
     *
     * @param cityNameLike letter
     * @return List of CityEntity
     */
    List<CityEntity> getCityWithThisLetters(String cityNameLike);

    /**
     * Removing city by id
     *
     * @param id city id
     */
    void removeCityById(@NotNull Long id);

    /**
     * Adding city
     *
     * @param cityEntity city data
     * @return CityEntity
     */
    CityEntity addCity(@NotNull CityEntity cityEntity) throws NotFoundException;

    /**
     * Updating city by id
     *
     * @param cityDto city data
     * @return CityEntity
     */
    CityEntity updateCityById(@NotNull Long id, CityEntity cityDto) throws NotFoundException;

    /**
     * Getting city by id
     *
     * @param id city id
     * @return CityEntity
     */
    CityEntity getCityById(@NotNull Long id);


}
