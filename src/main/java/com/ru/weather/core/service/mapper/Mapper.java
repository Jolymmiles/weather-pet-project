package com.ru.weather.core.service.mapper;

import com.ru.weather.core.model.openweatherapi.Temp;
import com.ru.weather.core.model.openweatherapi.Weather;
import com.ru.weather.core.service.dto.FalloutAndTemperatureDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Temp.class, FalloutAndTemperatureDto.class).field("day", "temperature").register();
        factory.classMap(Weather.class, FalloutAndTemperatureDto.class).field("description", "fallout").register();




    }
}