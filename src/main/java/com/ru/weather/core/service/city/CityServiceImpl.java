package com.ru.weather.core.service.city;

import com.ru.weather.api.controller.mapper.Mapper;
import com.ru.weather.db.entity.city.CityEntity;
import com.ru.weather.db.entity.city.CityEntityRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    Logger logger = LoggerFactory.getLogger("Логгер сервиса городов");

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
        if (getCityById(cityEntity.getId()) != null) {
            throw new NotFoundException("Уже существует");
        } else {
            return cityEntityRepository.save(cityEntity);
        }
    }

    public CityEntity updateCityById(Long id, CityEntity cityEntity) throws NotFoundException {
        logger.info("Обращение к updateCityById id = {}. Данные:{}",id, cityEntity);
        cityEntity.setId(id);
        if(getCityById(id) == null){
            throw new NotFoundException("Такого города нету");
        }
        return cityEntityRepository.save(cityEntity);


    }

    public CityEntity getCityById(Long id) {
        return cityEntityRepository.getById(id);
    }


}
