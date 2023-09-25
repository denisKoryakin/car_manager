package ru.koryakin.carmanager.controller.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarDTO {
    long id;
    String carNumber;
    String brand;
    String color;
    int releaseYear;
}

