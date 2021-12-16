package com.ru.weather.core.model.openweatherapi.other;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class FeelsLike {
    @SerializedName("day")
    private Double day;
    @SerializedName("night")
    private Double night;
    @SerializedName("eve")
    private Double eve;
    @SerializedName("morn")
    private Double morn;


}
