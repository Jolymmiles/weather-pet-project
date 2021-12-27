package com.ru.weather.core.service.weather;

import com.ru.weather.api.controller.mapper.Mapper;
import com.ru.weather.core.dto.WeatherDto;
import com.ru.weather.core.model.openweatherapi.current.Current;
import com.ru.weather.core.model.openweatherapi.other.AllData;
import com.ru.weather.core.model.openweatherapi.other.Daily;
import com.ru.weather.core.service.responseopenweatherapi.GettingDataFromOtherApi;
import com.ru.weather.db.entity.city.CityEntity;
import com.ru.weather.db.entity.weather.WeatherEntity;
import com.ru.weather.db.entity.weather.WeatherEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {
    @Autowired
    private WeatherEntityRepository weatherEntityRepository;

    @Autowired
    private Mapper mapper;

    @Autowired
    private GettingDataFromOtherApi gettingDataFromOtherApi;

    public WeatherEntity getWeatherByNow(CityEntity cityEntity) {
        LocalDate date = LocalDate.now();
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
            weatherToDataBase.setDate(Instant.ofEpochSecond(today.getDt()).atZone(ZoneId.systemDefault()).toLocalDate());
            return weatherEntityRepository.save(weatherToDataBase);
        }
    }


    public List<WeatherEntity> getAllCachedWeatherForThisCity(CityEntity cityEntity) {
        return weatherEntityRepository.findAllByCityEntity(cityEntity);
    }


    //Получение погоды на неделю
    public List<WeatherEntity> getWeeklyWeather(CityEntity cityEntity) {
        LocalDate localDate = LocalDate.now();
        List<WeatherEntity> weatherEntityList = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            WeatherEntity weatherEntityToList = weatherEntityRepository.findByCityEntityAndDate(cityEntity, localDate);
            localDate = localDate.plusDays(i);
            if (weatherEntityToList != null) {
                weatherEntityList.add(weatherEntityToList);
            }
        }
        if (weatherEntityList.size() == 7) {
            return weatherEntityList;
        } else {
            List<WeatherEntity> weatherEntities = new ArrayList<>();
            AllData allData = gettingDataFromOtherApi.getJsonFromOtherApi(cityEntity.getLatitude(), cityEntity.getLongitude());
            List<Daily> dailyList = allData.getDaily();
            for (Daily oneDay : dailyList) {
                WeatherDto weatherDto = mapper.map(oneDay, WeatherDto.class);
                LocalDate date = Instant.ofEpochSecond(oneDay.getDt()).atZone(ZoneId.systemDefault()).toLocalDate();
                if (weatherEntityRepository.findByCityEntityAndDate(cityEntity, date) == null) {
                    weatherDto.setDate(date);
                    WeatherEntity weatherEntityToDataBase = mapper.map(weatherDto, WeatherEntity.class);
                    weatherEntityToDataBase.setCityEntity(cityEntity);
                    weatherEntities.add(weatherEntityRepository.save(weatherEntityToDataBase));
                } else {
                    weatherEntities.add(weatherEntityRepository.findByCityEntityAndDate(cityEntity, date));
                }

            }
            return weatherEntities;
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


