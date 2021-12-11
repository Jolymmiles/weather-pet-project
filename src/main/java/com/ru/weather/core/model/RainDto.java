package com.ru.weather.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RainDto {
    private long id;
    private double oneHour;
    private double threeHour;

}
