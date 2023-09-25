package ru.koryakin.carmanager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.koryakin.carmanager.controller.DTO.Statistic;
import ru.koryakin.carmanager.repository.CarRepository;
import ru.koryakin.carmanager.repository.StatisticRepository;

@Service
@Slf4j
public class StatisticService {

    @Autowired
    StatisticRepository repository;

    public Statistic getStatistic() {
        return repository.getTableStatistic();
    }
}
