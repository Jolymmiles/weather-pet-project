package com.ru.weather.api.controller.mapper;

import com.ru.weather.core.model.*;
import com.ru.weather.db.entity.clouds.CloudsEntity;
import com.ru.weather.db.entity.rain.RainEntity;
import com.ru.weather.db.entity.temperature.TemperatureEntity;
import com.ru.weather.db.entity.weather.WeatherEntity;
import com.ru.weather.db.entity.wind.WindEntity;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {

        factory.classMap(CloudsEntity.class, CloudsDto.class).byDefault().register();

        factory.classMap(RainEntity.class, RainDto.class).byDefault().register();

        factory.classMap(TemperatureEntity.class, TemperatureDto.class).byDefault().register();

        factory.classMap(WeatherEntity.class, WeatherDto.class).byDefault().register();

        factory.classMap(WindEntity.class, WindDto.class).byDefault().register();
    }


}
