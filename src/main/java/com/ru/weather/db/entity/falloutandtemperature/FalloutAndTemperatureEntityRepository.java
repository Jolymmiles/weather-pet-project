package com.ru.weather.db.entity.falloutandtemperature;

import com.ru.weather.db.entity.falloutandtemperature.FalloutAndTemperatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FalloutAndTemperatureEntityRepository extends JpaRepository<FalloutAndTemperatureEntity, Long> {
}