package com.ru.weather.core.model.openweatherapi.other;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Daily {
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
    private Temp temp;
    @JsonProperty("feels_Like")
    private FeelsLike feelsLike;
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
    private List<Weather> weather;
    @JsonProperty("clouds")
    private Long clouds;
    @JsonProperty("pop")
    private Double pop;
    @JsonProperty("uvi")
    private Double uvi;
    @JsonProperty("snow")
    private Double snow;
    @JsonProperty("rain")
    private Double rain;
}
