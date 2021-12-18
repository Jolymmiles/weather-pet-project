package com.ru.weather.db.entity.weather;

import com.ru.weather.db.entity.weather.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherEntityRepository extends JpaRepository<WeatherEntity, Long> {
}