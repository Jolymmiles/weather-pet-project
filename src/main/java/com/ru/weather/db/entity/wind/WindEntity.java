package com.ru.weather.db.entity.wind;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "\"rains\"")
public class WindEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wind_seq")
    @SequenceGenerator(name = "wind_seq", sequenceName = "wind_sequence", allocationSize = 1)
    private Long id;
    @Column(name = "speed")
    private double speed;
    @Column(name = "deg")
    private int deg;

}
