package com.ru.weather.core.model.openweatherapi.other;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Weather {
    @SerializedName("id")
    private Integer id;
    @SerializedName("main")
    private String main;
    @SerializedName("description")
    private String description;
    @SerializedName("icon")
    private String icon;

}
