package com.ru.weather.db.entity.temperature;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "\"rains\"")
public class TemperatureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "temperature_seq")
    @SequenceGenerator(name = "temperature_seq", sequenceName = "tempreture_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "temp")
    private double temp;
    @Column(name = "feels_like")
    private double feelsLike;
    @Column(name = "temp_min")
    private double tempMmin;
    @Column(name = "tempMax")
    private double tempMax;
    @Column(name = "pressure")
    private double pressure;
    @Column(name = "humidity")
    private double humidity;

}
