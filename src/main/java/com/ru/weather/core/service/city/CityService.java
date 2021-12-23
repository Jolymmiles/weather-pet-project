package com.ru.weather.core.service.city;

import com.ru.weather.db.entity.city.CityEntity;
import com.ru.weather.db.entity.city.CityEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    @Autowired
    private CityEntityRepository cityEntityRepository;

    public CityEntity getByCityname(String cityname) {
        CityEntity cityEntity = cityEntityRepository.findByCityname(cityname);
        return cityEntity;
    }
}
