package ru.koryakin.carmanager.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.AssertionErrors;
import org.springframework.web.client.HttpClientErrorException;
import ru.koryakin.carmanager.controller.CarController;
import ru.koryakin.carmanager.controller.DTO.CarDTO;
import ru.koryakin.carmanager.entity.Car;
import ru.koryakin.carmanager.repository.CarRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.only;

@SpringJUnitConfig({
        CarService.class
})
@Slf4j
class CarServiceTest {

    @Autowired
    CarService service;

    @MockBean
    CarRepository repository;

    static Long startTime;
    static Long stopTime;

    @BeforeAll
    static void startTests() {
        startTime = System.nanoTime();
        log.info("Service tests start!");
    }

    @AfterAll
    static void testsIsDone() {
        stopTime = System.nanoTime();
        Long elapsed = (stopTime - startTime) / 1000000;
        log.info("All tests have been completed at" + elapsed + " ms");
    }

    @Test
    void getAllCarsWithException() {
        //arrange
        Car car1 = new Car();
        car1.setCarNumber("E666КХ96");
        car1.setBrand("Жигули");
        car1.setColor("Баклажан");
        car1.setReleaseYear(2000);

        Car car2 = new Car();
        car2.setCarNumber("A200AA77");
        car2.setBrand("Mercedes");
        car2.setColor("Черный");
        car2.setReleaseYear(2020);
        List<Car> cars = List.of(car1, car2);
        Mockito.when(repository.findAllCars(null, null, null)).thenReturn(cars);
        //act
        //assert
        Throwable exception = assertThrows(HttpClientErrorException.class, () -> service.getAllCars(null, null, null, null, null, null));
        assertEquals("400 BAD_REQUEST", exception.getMessage());
    }

    @Test
    void getAllCars() {
        Mockito.when(repository.findAllCars(null, null, null)).thenReturn(new ArrayList<>());
        assertEquals(0, service.getAllCars(null, null, null, null, null, null).size());
    }

    @Test
    void saveCar() {
        //arrange
        CarDTO car1 = new CarDTO(0, "E666КХ96", "Жигули", "Баклажан", 2000);
        //act
        service.saveCar(car1);
        //assert
        Mockito.verify(repository, only()).save(any(Car.class));
    }

    @Test
    void deleteCarWithException() {
        //arrange
        //act
        //assert
        Throwable exception = assertThrows(HttpClientErrorException.class, () -> service.deleteCar(1L));
        assertEquals("404 NOT_FOUND", exception.getMessage());
    }
}