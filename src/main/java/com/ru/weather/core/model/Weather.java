package com.ru.weather.core.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Weather {
    private int id;
    private String main;
    private String description;

}
