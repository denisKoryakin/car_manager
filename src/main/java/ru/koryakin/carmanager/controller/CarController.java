package ru.koryakin.carmanager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.koryakin.carmanager.controller.DTO.CarDTO;
import ru.koryakin.carmanager.service.CarService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService service;

    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars(@RequestParam(name = "brand", required = false) String brand,
                                                   @RequestParam(name = "color", required = false) String color,
                                                   @RequestParam(name = "releaseYear", required = false) Integer releaseYear,
                                                   @RequestParam(name = "brandSort", required = false) String brandSort,
                                                   @RequestParam(name = "colorSort", required = false) String colorSort,
                                                   @RequestParam(name = "releaseYearSort", required = false) String releaseYearSort) {
        CacheControl cacheControl = CacheControl.maxAge(60, TimeUnit.SECONDS)
                .noTransform()
                .mustRevalidate();
        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(service.getAllCars(brand, color, releaseYear, brandSort, colorSort, releaseYearSort));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveCar(@RequestBody CarDTO car) {
        service.saveCar(car);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable("id") Long id) {
        service.deleteCar(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
