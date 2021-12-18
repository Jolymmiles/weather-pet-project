package com.ru.weather.core.service.dto;

import lombok.Data;

@Data
public class FalloutAndTemperatureDto {
    private Long id;
    private Double temperature;
    private String fallout;
}
