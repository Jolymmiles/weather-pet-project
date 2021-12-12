package com.ru.weather.db.entity;

import com.ru.weather.core.service.dto.CityDto;
import com.ru.weather.core.service.dto.FalloutAndTemperatureDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"weather\"")
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_seq")
    @SequenceGenerator(name = "weather_seq", sequenceName = "weather_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "date_of_weather")
    private String dateOfWeather;
    @OneToOne
    @JoinColumn(name = "city_id")
    private CityEntity cityEntity;
    @OneToMany
    @JoinColumn(name = "temp_and_fall_id")
    private FalloutAndTemperatureEntity falloutAndTemperatureEntity;
    @Column(name = "squeeze")
    private Integer squeeze;
    @Column(name = "wet")
    private Integer wet;
    @Column(name = "breeze_rate")
    private String breezeRate;
    @Column(name = "date_of_getting_data")
    private String dateOfGettingData;
}
