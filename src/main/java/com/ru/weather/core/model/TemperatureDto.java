package com.ru.weather.core.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class TemperatureDto {
    private long id;
    private double temp;
    private double feelsLike;
    private double tempMin;
    private double tempMax;
    private double pressure;
    private double humidity;
}
