package com.ru.weather.db.entity.weather;

import com.ru.weather.db.entity.city.CityEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"weather\"")
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_seq")
    @SequenceGenerator(name = "weather_seq", sequenceName = "weather_sequence", allocationSize = 1)
    @Column(name = "weather_id", nullable = false)
    private Long id;
    @Column(name = "date")
    private Date date;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cityid")
    private CityEntity cityEntity;
    @Column(name = "temperature")
    private Double temperature;
    @Column(name = "date_of_getting_data")
    private Date dateOfGettingData;

}
