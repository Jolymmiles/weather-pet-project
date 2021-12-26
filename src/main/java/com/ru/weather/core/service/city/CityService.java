package com.ru.weather.core.service.city;

import com.ru.weather.core.dto.CityDto;
import com.ru.weather.db.entity.city.CityEntity;

public interface CityService {
    /**
     * Getting city by name
     * @param cityname  name of the city
     * @return CityEntity
     */
    public CityEntity getByCityname(String cityname);

    /**
     * Removing city by id
     * @param id  city id
     */
    public void removeCitById(Long id);

    /**
     * Adding city
     * @param cityDto  city data
     * @return CityEntity
     */
    public CityEntity addCity(CityDto cityDto);

    /**
     * Updating city by id
     * @param cityDto  city data
     * @return CityEntity
     */
    public CityEntity updateCityById(CityDto cityDto);

    /**
     * Getting city by id
     * @param id  city id
     * @return CityEntity
     */
    public CityEntity getCityById(Long id);
}
