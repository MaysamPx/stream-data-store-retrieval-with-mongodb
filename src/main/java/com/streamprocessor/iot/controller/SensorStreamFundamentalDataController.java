package com.streamprocessor.iot.controller;

import com.streamprocessor.iot.constants.GlobalVariables;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@EnableCaching
@RequestMapping("/api/v1")
@Tag(name = "Sensor's Fundamental Data", description = "REST API for the Concrete fields of sensors.")
public class SensorStreamFundamentalDataController {

    @GetMapping(value = "/sensors", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a list of all sensors.")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = List.class)))
    public ResponseEntity<List<String>> getSensorList() {
        List<String> sensorList = Arrays.asList(GlobalVariables.SENSOR_NAMES);
        return ResponseEntity.ok(sensorList);
    }
}