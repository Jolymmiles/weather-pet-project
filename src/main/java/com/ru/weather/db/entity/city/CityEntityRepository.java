package com.ru.weather.db.entity.city;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityEntityRepository extends JpaRepository<CityEntity, Long> {
    CityEntity findByName(String cityname);
    List<CityEntity> findByNameContains(String letters);
}