package com.ru.weather.core.model.openweatherapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Rain {
    @JsonProperty("1h")
    private Double onehour;
    @JsonProperty("3h")
    private Double threeHour;
}
