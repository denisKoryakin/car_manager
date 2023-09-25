package ru.koryakin.carmanager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Car {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "car_number")
    private String carNumber;

    private String brand;

    private String color;

    @Column(name = "release_year")
    private int releaseYear;

    private LocalDateTime created;
}
