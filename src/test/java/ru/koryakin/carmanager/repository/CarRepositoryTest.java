package ru.koryakin.carmanager.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.koryakin.carmanager.entity.Car;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(initializers = {RepositoryTestInitializer.Initializer.class})
@Testcontainers
@Slf4j
class CarRepositoryTest extends RepositoryTestInitializer {

    @Autowired
    CarRepository repository;

    static Long startTime;
    static Long stopTime;

    @Transactional
    @BeforeEach
    void inserData() {
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
        repository.save(car1);
        repository.save(car2);
    }

    @AfterEach
    private void deleteData() {
        repository.deleteAll();
    }

    @BeforeAll
    static void startTests() {
        startTime = System.nanoTime();
        log.info("CarRepository tests start!");
    }

    @AfterAll
    static void testsIsDone() {
        stopTime = System.nanoTime();
        Long elapsed = (stopTime - startTime)/1000000;
        log.info("All tests have been completed at" + elapsed + " ms");
    }

    @ParameterizedTest
    @MethodSource("provideParametersForFindAllCars")
    void findAllCars(String brand, String color, Integer releaseYear, int result) {
        //act
        final var cars = repository.findAllCars(brand, color, releaseYear);
        //assert
        assertEquals(result, cars.size());
    }

    private static Stream<Arguments> provideParametersForFindAllCars() {
        return Stream.of(
                Arguments.of(null, null, null, 2),
                Arguments.of("Жигули", null, null, 1),
                Arguments.of(null, "Черный", null, 1),
                Arguments.of(null, null, 2020, 1)
        );
    }
}