package com.ru.weather.db.entity.falloutandtemperature;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"falloutandtemperatures\"")
public class FalloutAndTemperatureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "falloutandtemperatures_seq")
    @SequenceGenerator(name = "falloutandtemperatures_seq", sequenceName = "falloutandtemperatures_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "temperature")
    private Double temperature;
    @Column(name = "fallout")
    private String fallout;

}
