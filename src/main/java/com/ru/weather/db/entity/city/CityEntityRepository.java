package com.ru.weather.db.entity.city;

import com.ru.weather.db.entity.city.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityEntityRepository extends JpaRepository<CityEntity, Long> {
}