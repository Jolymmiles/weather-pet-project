package com.ru.weather.core.mapper;

import com.ru.weather.core.model.CloudsDto;
import com.ru.weather.db.entity.clouds.CloudsEntity;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory){
        factory.classMap(CloudsEntity.class, CloudsDto.class).byDefault().register();
    }


}
