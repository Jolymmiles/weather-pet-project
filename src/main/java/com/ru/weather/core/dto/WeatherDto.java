package com.ru.weather.core.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeatherDto {
    private Long id;
    private LocalDate date;
    private CityDto cityDto;
    private Double temperature;
    private String weatherCondition;
    private LocalDate dateOfGettingData;

}
