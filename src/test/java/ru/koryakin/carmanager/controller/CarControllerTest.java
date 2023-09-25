package ru.koryakin.carmanager.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.koryakin.carmanager.controller.DTO.CarDTO;
import ru.koryakin.carmanager.service.CarService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.only;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringJUnitConfig({
        CarController.class
})
@Slf4j
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = CarController.class)
class CarControllerTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<CarDTO> captor;

    @MockBean
    CarService service;

    static Long startTime;
    static Long stopTime;

    CarDTO car1 = new CarDTO(0, "E666КХ96", "Жигули", "Баклажан", 2000);
    CarDTO car2 = new CarDTO(0, "A200AA77", "Mercedes", "Черный", 2020);
    List<CarDTO> cars = List.of(car1, car2);

    @BeforeAll
    static void startTests() {
        startTime = System.nanoTime();
        log.info("Controller tests start!");
    }

    @AfterAll
    static void testsIsDone() {
        stopTime = System.nanoTime();
        Long elapsed = (stopTime - startTime) / 1000000;
        log.info("All tests have been completed at" + elapsed + " ms");
    }

    @Test
    void connectionHandlerTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllCars() throws Exception {
        Mockito.when(service.getAllCars(null, null, null, null, null, null)).thenReturn(cars);
        mockMvc.perform(MockMvcRequestBuilders.get("/cars"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].brand", is("Mercedes")))
                .andExpect(MockMvcResultMatchers.header()
                        .string("Cache-Control", "max-age=60, must-revalidate, no-transform"));
    }

    @Test
    void saveCar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/cars")
                .accept(MediaType.ALL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{ \"carNumber\": \"E666КХ96\", \"brand\": \"Жигули\", \"color\": \"Баклажан\", \"releaseYear\": 2000 }"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, only()).saveCar(captor.capture());
        var result = captor.getValue();
        assertEquals("Жигули", result.getBrand());
        assertEquals("Баклажан", result.getColor());
    }

    @Test
    void deleteCar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/cars/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, only()).deleteCar(eq(1L));
    }
}