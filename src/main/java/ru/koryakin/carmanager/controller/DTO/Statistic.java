package ru.koryakin.carmanager.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Statistic {

    LocalDateTime firstRecord;
    LocalDateTime lastRecord;
    long recordCount;
}
