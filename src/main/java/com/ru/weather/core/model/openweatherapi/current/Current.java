package com.ru.weather.core.model.openweatherapi.current;



import com.google.gson.annotations.SerializedName;
import com.ru.weather.core.model.openweatherapi.other.Weather;
import lombok.Data;

import java.util.List;


@Data
public class Current {
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
    private Double temp;
    @SerializedName("feels_like")
    private Double feelsLike;
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
    private List<Weather> weatherArray;
    @SerializedName("clouds")
    private Long clouds;
    @SerializedName("uvi")
    private Double uvi;
    @SerializedName("rain")
    private Rain rain;
    @SerializedName("snow")
    private Snow snow;
}
