package com.ru.weather.api.controller.mapper;


import com.ru.weather.core.service.dto.CityDto;
import com.ru.weather.core.service.dto.FalloutAndTemperatureDto;
import com.ru.weather.core.service.dto.WeatherDto;
import com.ru.weather.db.entity.CityEntity;
import com.ru.weather.db.entity.FalloutAndTemperatureEntity;
import com.ru.weather.db.entity.WeatherEntity;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(CityEntity.class, CityEntity.class).byDefault().register();

        factory.classMap(FalloutAndTemperatureEntity.class, FalloutAndTemperatureDto.class).byDefault().register();

        factory.classMap(WeatherEntity.class, WeatherDto.class).byDefault().register();

    }


}
