package com.ru.weather.db.entity.weather;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface WeatherEntityRepository extends JpaRepository<WeatherEntity, Long> {
    //WeatherEntity findByCityname(String cityname);

    @Query("select CityEntity from WeatherEntity where CityEntity.id = :cityid and WeatherEntity.date = :date")
    WeatherEntity findByCityidAndDate(@Param("cityid") Long cityid, @Param("date") Date date);
}