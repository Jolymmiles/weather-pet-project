package com.ru.weather.core.model.openweatherapi.current;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Rain {
    @JsonProperty("1h")
    private Double onehour;
    @JsonProperty("3h")
    private Double threeHour;
}
