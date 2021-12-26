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

    /*
"current": {
		"dt": 1640411699,
		"sunrise": 1640429633,
		"sunset": 1640460575,
		"temp": 0.56,
		"feels_like": -6.44,
		"pressure": 991,
		"humidity": 69,
		"dew_point": -3.95,
		"uvi": 0,
		"clouds": 100,
		"visibility": 10000,
		"wind_speed": 14.1,
		"wind_deg": 246,
		"wind_gust": 18.96,
		"weather": [
			{
				"id": 804,
				"main": "Clouds",
				"description": "overcast clouds",
				"icon": "04n"
			}
		]
     */


}
