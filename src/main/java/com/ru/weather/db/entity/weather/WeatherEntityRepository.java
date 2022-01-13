package com.ru.weather.db.entity.weather;

import com.ru.weather.db.entity.city.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface WeatherEntityRepository extends JpaRepository<WeatherEntity, Long> {
    WeatherEntity findByCityEntityAndDateOfWeather(CityEntity cityEntity, LocalDate date);

    List<WeatherEntity> findAllByCityEntity(CityEntity cityEntity);

    List<WeatherEntity> findByCityEntity_NameContains(String letters);
}