package com.ru.weather.db.entity.rain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "\"rains\"")
public class RainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rains_seq")
    @SequenceGenerator(name = "rains_seq", sequenceName = "rains_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "onehour")
    private double oneHour;
    @Column(name = "threehour")
    private double threeHour;

}
