package com.ru.weather.db.entity.temperature;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TemperatureRepository extends JpaRepository<TemperatureEntity, Long> {
}
