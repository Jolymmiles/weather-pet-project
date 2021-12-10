package com.ru.weather.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class WindDto {
    private double speed;
    private int deg;
}
