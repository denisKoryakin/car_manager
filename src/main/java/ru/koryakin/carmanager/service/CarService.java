package ru.koryakin.carmanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.koryakin.carmanager.controller.DTO.CarDTO;
import ru.koryakin.carmanager.controller.DTO.CarDtoComparator;
import ru.koryakin.carmanager.entity.Car;
import ru.koryakin.carmanager.repository.CarRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarService {

    private final CarRepository repository;

    public List<CarDTO> getAllCars(String brand, String color, Integer releaseYear, String brandSort, String colorSort, String releaseYearSort) {
        try {
            List<Car> cars = repository.findAllCars(brand, color, releaseYear);
            return cars.stream().map(car -> new CarDTO(
                    car.getId(),
                    car.getCarNumber(),
                    car.getBrand(),
                    car.getColor(),
                    car.getReleaseYear())
            )
                    .sorted(((o1, o2) -> new CarDtoComparator(brandSort, colorSort, releaseYearSort).compare(o1, o2)))
                    .collect(Collectors.toList());
        } catch (RuntimeException ex) {
            log.error("Server error: {}", ex.getMessage());
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void saveCar(CarDTO car) {
        try {
            Car newCar = new Car();
            newCar.setCarNumber(car.getCarNumber());
            newCar.setBrand(car.getBrand());
            newCar.setColor(car.getColor());
            newCar.setReleaseYear(car.getReleaseYear());
            newCar.setCreated(LocalDateTime.now());
            repository.save(newCar);
        } catch (Exception ex) {
            log.error("saveCar error: {}", ex.getMessage());
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteCar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
