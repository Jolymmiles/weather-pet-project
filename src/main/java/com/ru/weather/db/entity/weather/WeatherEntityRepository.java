package com.ru.weather.db.entity.weather;

import com.ru.weather.db.entity.city.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface WeatherEntityRepository extends JpaRepository<WeatherEntity, Long> {
    //WeatherEntity findByCityname(String cityname);

    WeatherEntity findByCityEntityAndDate(CityEntity cityId, LocalDate date);
}