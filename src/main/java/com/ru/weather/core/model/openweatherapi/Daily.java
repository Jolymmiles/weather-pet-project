package com.ru.weather.core.model.openweatherapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
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
    @JsonProperty("feelsLike")
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
    private Weather weather;
    @JsonProperty("clouds")
    private Long clouds;
    @JsonProperty("pop")
    private Double pop;
    @JsonProperty("uvi")
    private Double uvi;
}
