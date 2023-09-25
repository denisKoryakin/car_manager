package ru.koryakin.carmanager.controller.DTO;

import lombok.AllArgsConstructor;

import java.util.Comparator;

@AllArgsConstructor
public class CarDtoComparator implements Comparator<CarDTO> {

    private String brandSort;
    private String colorSort;
    private String releaseYearSort;

    @Override
    public int compare(CarDTO o1, CarDTO o2) {
        if (brandSort != null) {
            return o1.getBrand().compareTo(o2.getBrand());
        } else if (colorSort != null) {
            return o1.getColor().compareTo(o2.getColor());
        } else if (releaseYearSort != null) {
            return o1.getReleaseYear() < (o2.getReleaseYear()) ? -1 : 1;
        } else return 0;
    }
}
