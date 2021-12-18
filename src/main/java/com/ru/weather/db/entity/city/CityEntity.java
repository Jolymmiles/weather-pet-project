package com.ru.weather.db.entity.city;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"cities\"")
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_seq")
    @SequenceGenerator(name = "city_seq", sequenceName = "city_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "cityname")
    private String name;
    @Column(name = "lat")
    private Double latitude;
    @Column(name = "lon")
    private Double longitude;


}
