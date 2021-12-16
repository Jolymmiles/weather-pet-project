package com.ru.weather.core.model.openweatherapi.current;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Snow {
    @SerializedName("1h")
    private Double onehour;
    @SerializedName("3h")
    private Double threeHour;
}
