package com.ru.weather.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CityDto {
    @ApiModelProperty("Id записи")
    private Long id;
    @ApiModelProperty("Название города")
    private String name;
    @ApiModelProperty("Широта")
    private Double latitude;
    @ApiModelProperty("Долгота")
    private Double longitude;

}
