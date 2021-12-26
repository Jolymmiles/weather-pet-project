package com.ru.weather.api.controller.mapper;


import com.ru.weather.core.dto.WeatherDto;
import com.ru.weather.core.model.openweatherapi.current.Current;
import com.ru.weather.db.entity.city.CityEntity;
import com.ru.weather.db.entity.weather.WeatherEntity;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(CityEntity.class, CityEntity.class).byDefault().register();

        factory.classMap(WeatherEntity.class, WeatherDto.class)
                .fieldAToB("cityEntity","cityDto")
                .byDefault()
                .register();

        factory.classMap(Current.class, WeatherDto.class)
                .field("temp","temperature")
                .register();


    }


}
