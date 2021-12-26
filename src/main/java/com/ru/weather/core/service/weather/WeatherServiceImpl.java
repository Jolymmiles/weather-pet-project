package com.ru.weather.core.service.weather;

import com.ru.weather.api.controller.mapper.Mapper;
import com.ru.weather.core.dto.WeatherDto;
import com.ru.weather.core.model.openweatherapi.current.Current;
import com.ru.weather.core.model.openweatherapi.other.AllData;
import com.ru.weather.core.service.responseopenweatherapi.GettingDataFromOtherApi;
import com.ru.weather.db.entity.city.CityEntity;
import com.ru.weather.db.entity.weather.WeatherEntity;
import com.ru.weather.db.entity.weather.WeatherEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class WeatherServiceImpl implements WeatherService {
    @Autowired
    private WeatherEntityRepository weatherEntityRepository;

    @Autowired
    private Mapper mapper;

    @Autowired
    private GettingDataFromOtherApi gettingDataFromOtherApi;

    public WeatherEntity getWeatherByDate(CityEntity cityEntity, LocalDate date) {
        WeatherEntity weatherEntity = weatherEntityRepository.findByCityEntityAndDate(cityEntity, date);
        if (weatherEntity != null) {
            return weatherEntity;
        } else {
            Instant something = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
            Long unixTime = something.toEpochMilli();
            AllData allData = gettingDataFromOtherApi.getJsonFromOtherApi(cityEntity.getLatitude(), cityEntity.getLongitude(), unixTime);
            Current today = allData.getCurrent();
            WeatherDto weatherDto = mapper.map(today, WeatherDto.class);
            WeatherEntity weatherToDataBase = mapper.map(weatherDto, WeatherEntity.class);
            weatherToDataBase.setCityEntity(cityEntity);
            weatherToDataBase.setDate(date);
            WeatherEntity entityToReturn = weatherEntityRepository.save(weatherToDataBase);
            return entityToReturn;
        }
    }

    //Стандартные запросы
    public void removeWeatherById(Long id) {
        weatherEntityRepository.deleteById(id);
    }

    public WeatherEntity addWeather(WeatherDto weatherDto) {
        return weatherEntityRepository.save(mapper.map(weatherDto, WeatherEntity.class));
    }

    public WeatherEntity updateWeatherById(WeatherDto weatherDto) {
        if (weatherDto.getId() != null) {
            return weatherEntityRepository.save(mapper.map(weatherDto, WeatherEntity.class));
        } else
            return null;

    }

    public WeatherEntity getWeatherById(Long id) {
        return weatherEntityRepository.getById(id);
    }


}


