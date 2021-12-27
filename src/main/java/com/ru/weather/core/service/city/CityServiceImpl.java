package com.ru.weather.core.service.city;

import com.ru.weather.api.controller.mapper.Mapper;
import com.ru.weather.core.dto.CityDto;
import com.ru.weather.db.entity.city.CityEntity;
import com.ru.weather.db.entity.city.CityEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private CityEntityRepository cityEntityRepository;

    @Autowired
    private Mapper mapper;

    public CityEntity getByCityName(String cityname) {
        return cityEntityRepository.findByName(cityname);
    }


    //Стандартные запросы
    public void removeCityById(Long id) {
        cityEntityRepository.deleteById(id);
    }

    public CityEntity addCity(CityDto cityDto) {
        return cityEntityRepository.save(mapper.map(cityDto, CityEntity.class));
    }

    public CityEntity updateCityById(CityDto cityDto) {
        if (cityDto.getId() != null) {
            return cityEntityRepository.save(mapper.map(cityDto, CityEntity.class));
        } else
            return null;

    }

    public CityEntity getCityById(Long id) {
        return cityEntityRepository.getById(id);
    }


}
