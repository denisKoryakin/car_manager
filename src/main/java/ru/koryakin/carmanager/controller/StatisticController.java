package ru.koryakin.carmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.koryakin.carmanager.service.StatisticService;

@RestController
@RequestMapping("/statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService service;

    @GetMapping
    public ResponseEntity<?> getStatistic() {
        return ResponseEntity.ok(service.getStatistic());
    }
}
