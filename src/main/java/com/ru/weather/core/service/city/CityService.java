package com.ru.weather.core.service.city;

import com.ru.weather.core.dto.CityDto;
import com.ru.weather.db.entity.city.CityEntity;

public interface CityService {
    /**
     * Getting city by name
     *
     * @param cityName name of the city
     * @return CityEntity
     */
    CityEntity getByCityName(String cityName);

    /**
     * Removing city by id
     *
     * @param id city id
     */
    void removeCityById(Long id);

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
    CityEntity updateCityById(CityDto cityDto);

    /**
     * Getting city by id
     *
     * @param id city id
     * @return CityEntity
     */
    CityEntity getCityById(Long id);


}
