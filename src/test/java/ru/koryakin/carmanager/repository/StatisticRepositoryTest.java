package ru.koryakin.carmanager.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.koryakin.carmanager.controller.DTO.Statistic;
import ru.koryakin.carmanager.entity.Car;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(initializers = {RepositoryTestInitializer.Initializer.class})
@Testcontainers
@Slf4j
class StatisticRepositoryTest extends RepositoryTestInitializer {

    @Autowired
    StatisticRepository statisticRepository;

    @Autowired
    CarRepository carRepository;

    static Long startTime;
    static Long stopTime;

    LocalDateTime car1Time = LocalDateTime.now().minusDays(1);
    LocalDateTime car2Time = LocalDateTime.now().plusDays(1);

    @Transactional
    @BeforeEach
    void inserData() {
        Car car1 = new Car();
        car1.setCarNumber("E666КХ96");
        car1.setBrand("Жигули");
        car1.setColor("Баклажан");
        car1.setReleaseYear(2000);
        car1.setCreated(car1Time);

        Car car2 = new Car();
        car2.setCarNumber("A200AA77");
        car2.setBrand("Mercedes");
        car2.setColor("Черный");
        car2.setReleaseYear(2020);
        car2.setCreated(car2Time);
        carRepository.save(car1);
        carRepository.save(car2);
    }

    @AfterEach
    private void deleteData() {
        carRepository.deleteAll();
    }

    @BeforeAll
    static void startTests() {
        startTime = System.nanoTime();
        log.info("StatisticRepository tests start!");
    }

    @AfterAll
    static void testsIsDone() {
        stopTime = System.nanoTime();
        Long elapsed = (stopTime - startTime)/1000000;
        log.info("All tests have been completed at" + elapsed + " ms");
    }

    @Test
    void getTableStatistic() {
        Statistic statisic = statisticRepository.getTableStatistic();
        assertEquals(2, statisic.getRecordCount());
        assertEquals(car1Time.getSecond(), statisic.getFirstRecord().getSecond());
        assertEquals(car2Time.getSecond(), statisic.getLastRecord().getSecond());
    }
}