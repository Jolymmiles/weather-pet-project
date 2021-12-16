package com.ru.weather.core.model.openweatherapi.other;

import com.google.gson.annotations.SerializedName;
import com.ru.weather.core.model.openweatherapi.current.Current;
import lombok.Data;

import java.util.List;

@Data
public class AllData {
    @SerializedName("lat")
    private Double lat;
    @SerializedName("lon")
    private Double lon;
    @SerializedName("timezone")
    private String timezone;
    @SerializedName("timezone_offset")
    private Integer timezoneOffset;
    @SerializedName("current")
    private Current current;
    @SerializedName("daily")
    private List<Daily> daily;


}
