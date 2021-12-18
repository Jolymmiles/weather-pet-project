package com.ru.weather.core.model.openweatherapi.current;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.ru.weather.core.model.openweatherapi.other.Weather;
import lombok.Data;

import java.util.List;


@Data
public class Current {
    @JsonProperty("dt")
    private Long dt;
    @JsonProperty("sunrise")
    private Long sunrise;
    @JsonProperty("sunset")
    private Long sunset;
    @JsonProperty("moonrise")
    private Long moonrise;
    @JsonProperty("moonset")
    private Long moonset;
    @JsonProperty("moon_phase")
    private Double moonPhase;
    @JsonProperty("temp")
    private Double temp;
    @JsonProperty("feels_like")
    private Double feelsLike;
    @JsonProperty("pressure")
    private Long pressure;
    @JsonProperty("humidity")
    private Long humidity;
    @JsonProperty("dew_point")
    private Double dewPoint;
    @JsonProperty("wind_speed")
    private Double windSpeed;
    @JsonProperty("wind_deg")
    private Long windDeg;
    @JsonProperty("wind_gust")
    private Double windGust;
    @JsonProperty("weather")
    private List<Weather> weatherArray;
    @JsonProperty("clouds")
    private Long clouds;
    @JsonProperty("uvi")
    private Double uvi;
    @JsonProperty("rain")
    private Rain rain;
    @JsonProperty("snow")
    private Snow snow;
}
