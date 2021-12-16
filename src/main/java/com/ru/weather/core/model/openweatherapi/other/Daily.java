package com.ru.weather.core.model.openweatherapi.other;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class Daily {
    @SerializedName("dt")
    private Long dt;
    @SerializedName("sunrise")
    private Long sunrise;
    @SerializedName("sunset")
    private Long sunset;
    @SerializedName("moonrise")
    private Long moonrise;
    @SerializedName("moonset")
    private Long moonset;
    @SerializedName("moon_phase")
    private Double moonPhase;
    @SerializedName("temp")
    private Temp temp;
    @SerializedName("feels_Like")
    private FeelsLike feelsLike;
    @SerializedName("pressure")
    private Long pressure;
    @SerializedName("humidity")
    private Long humidity;
    @SerializedName("dew_point")
    private Double dewPoint;
    @SerializedName("wind_speed")
    private Double windSpeed;
    @SerializedName("wind_deg")
    private Long windDeg;
    @SerializedName("wind_gust")
    private Double windGust;
    @SerializedName("weather")
    private List<Weather> weather;
    @SerializedName("clouds")
    private Long clouds;
    @SerializedName("pop")
    private Double pop;
    @SerializedName("uvi")
    private Double uvi;
    @SerializedName("snow")
    private Double snow;
    @SerializedName("rain")
    private Double rain;
}
