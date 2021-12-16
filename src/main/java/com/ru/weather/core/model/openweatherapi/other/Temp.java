package com.ru.weather.core.model.openweatherapi.other;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
class Temp {
    @SerializedName("temp")
    private Double temp;
    @SerializedName("day")
    private Double day;
    @SerializedName("min")
    private Double min;
    @SerializedName("max")
    private Double max;
    @SerializedName("night")
    private Double night;
    @SerializedName("eve")
    private Double eve;
    @SerializedName("morn")
    private Double morn;
}
