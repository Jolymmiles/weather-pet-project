package com.ru.weather.core.model.openweatherapi.other;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
class Temp {
    @JsonProperty("temp")
    private Double temp;
    @JsonProperty("day")
    private Double day;
    @JsonProperty("min")
    private Double min;
    @JsonProperty("max")
    private Double max;
    @JsonProperty("night")
    private Double night;
    @JsonProperty("eve")
    private Double eve;
    @JsonProperty("morn")
    private Double morn;
}
