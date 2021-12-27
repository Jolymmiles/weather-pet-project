package com.ru.weather.db.entity.weather;

import com.ru.weather.db.entity.city.CityEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id")
    private CityEntity cityEntity;
    @Column(name = "temperature")
    private Double temperature;
    @Column(name = "weather_condition")
    private String weatherCondition;
    @CreatedDate
    @Column(name = "date_of_getting_data")
    private LocalDate dateOfGettingData;

}
