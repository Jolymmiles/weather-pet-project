package com.ru.weather.core.service.city;

import com.ru.weather.api.controller.mapper.Mapper;
import com.ru.weather.db.entity.city.CityEntity;
import com.ru.weather.db.entity.city.CityEntityRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private CityEntityRepository cityEntityRepository;

    @Autowired
    private Mapper mapper;

    public CityEntity getByCityName(String cityname) {
        return cityEntityRepository.findByName(cityname);
    }

    public List<CityEntity> getAllCity() {
        return cityEntityRepository.findAll();
    }

    public List<CityEntity> getCityWithThisLetters(String letters) {
        if (letters == null) {
            return cityEntityRepository.findAll();
        }
        return cityEntityRepository.findByNameContains(letters);
    }

    public void removeCityById(Long id) {
        cityEntityRepository.deleteById(id);
    }

    public CityEntity addCity(CityEntity cityEntity) throws NotFoundException {
        CityEntity check = getByCityName(cityEntity.getName());
        if (check != null) {
            throw new NotFoundException("Уже существует");
        } else {
            return cityEntityRepository.save(cityEntity);
        }
    }

    public CityEntity updateCityById(Long id, CityEntity cityEntity) {
        cityEntity.setId(id);
        return cityEntityRepository.save(cityEntity);


    }

    public CityEntity getCityById(Long id) {
        return cityEntityRepository.getById(id);
    }


}
