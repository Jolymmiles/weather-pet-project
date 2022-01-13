package com.ru.weather.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class WeatherDto {
    @ApiModelProperty("Id записи")
    private Long id;
    @ApiModelProperty("Дата погоды")
    private LocalDate dateOfWeather;
    @ApiModelProperty("Город")
    private CityDto cityDto;
    @ApiModelProperty("Температура")
    private Double temperature;
    @ApiModelProperty("Состояние")
    private String weatherCondition;
    @ApiModelProperty("Дата получения данных")
    private LocalDate dateOfGettingData;
    @ApiModelProperty("Ссылка на иконку")
    private String icon;

}
