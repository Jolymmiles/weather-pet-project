package com.ru.weather.core.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CityDto {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;

}
