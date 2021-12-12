package com.ru.weather.core.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class FalloutAndTemperatureDto {
    private Long id;
    private Double temperature;
    private String fallout;
}
