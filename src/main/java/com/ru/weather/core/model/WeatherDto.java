package com.ru.weather.core.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class WeatherDto {
    private int id;
    private String main;
    private String description;

}
