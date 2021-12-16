package com.ru.weather.core.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class FalloutAndTemperatureDto {
    private Long id;
    private Double temperature;
    private String fallout;
}
