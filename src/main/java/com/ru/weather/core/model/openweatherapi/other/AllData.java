package com.ru.weather.core.model.openweatherapi.other;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ru.weather.core.model.openweatherapi.current.Current;
import lombok.Data;

import java.util.List;

@Data
public class AllData {
    @JsonProperty("lat")
    private Double lat;
    @JsonProperty("lon")
    private Double lon;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("timezone_offset")
    private Integer timezoneOffset;
    @JsonProperty("current")
    private Current current;
    @JsonProperty("daily")
    private List<Daily> daily;


}
