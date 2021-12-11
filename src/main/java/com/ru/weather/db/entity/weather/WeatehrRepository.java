package com.ru.weather.db.entity.weather;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatehrRepository extends JpaRepository<WeatherEntity, Long> {
}
