package com.ru.weather.core.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class WeatherDto {
    private Long id;
    private String dateOfWeather;
    private CityDto cityDto;
    private FalloutAndTemperatureDto falloutAndTemperatureDto;
    private Integer squeeze;
    private Integer wet;
    private String breezeRate;
    private String dateOfGettingData;

}
