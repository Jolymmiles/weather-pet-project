package com.ru.weather.db.entity.clouds;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "\"clouds\"")
public class CloudsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="clouds_seq")
    @SequenceGenerator(name="clouds_seq", sequenceName = "clouds_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "name_all")
    private int all;
}
