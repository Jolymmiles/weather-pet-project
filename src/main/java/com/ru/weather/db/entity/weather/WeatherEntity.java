package com.ru.weather.db.entity.weather;

import com.ru.weather.db.entity.city.CityEntity;
import com.ru.weather.db.entity.falloutandtemperature.FalloutAndTemperatureEntity;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "\"weather\"")
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_seq")
    @SequenceGenerator(name = "weather_seq", sequenceName = "weather_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "date_of_weather")
    private LocalDate dateOfWeather;
    @OneToOne
    @JoinColumn(name = "city_id")
    private CityEntity cityEntity;
    @OneToMany
    @JoinColumn(name = "temp_and_fall_id")
    @ToString.Exclude
    private FalloutAndTemperatureEntity falloutAndTemperatureEntity;
    @Column(name = "squeeze")
    private Integer squeeze;
    @Column(name = "wet")
    private Integer wet;
    @Column(name = "breeze_rate")
    private String breezeRate;
    @Column(name = "date_of_getting_data")
    private LocalDate dateOfGettingData;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WeatherEntity that = (WeatherEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
