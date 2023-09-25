package ru.koryakin.carmanager.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> httpClientExceptionHandler(HttpClientErrorException ex) {
        HttpStatus code = HttpStatus.valueOf(ex.getStatusCode().value());

        return switch (code) {
            case BAD_REQUEST -> new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
            case INTERNAL_SERVER_ERROR -> new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
            case NOT_FOUND -> new ResponseEntity<>("Car is not found", HttpStatus.NOT_FOUND);
            default -> new ResponseEntity<>("Undefined error", HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }
}
