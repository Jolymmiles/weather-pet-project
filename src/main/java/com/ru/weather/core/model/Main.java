package com.ru.weather.core.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class Main {
    private double temp;
    private double feels_like;
    private double temp_min;
    private double temp_max;
    private double pressure;
    private double humidity;
}
