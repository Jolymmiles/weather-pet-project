package com.ru.weather.db.entity.weather;

import com.ru.weather.db.entity.city.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WeatherEntityRepository extends JpaRepository<WeatherEntity, Long> {


    WeatherEntity findByCityEntityAndDateOfWeather(CityEntity cityEntity, LocalDate date);

    List<WeatherEntity> findAllByCityEntity(CityEntity cityEntity);
}