package com.streamprocessor.iot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SensorExceptionHandler {

    @ExceptionHandler(SensorNotFoundException.class)
    public ResponseEntity<String> handleSensorNotFoundException(SensorNotFoundException ex) {
        // log the exception
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Sensor not found: " + ex.getSensor());
    }

}