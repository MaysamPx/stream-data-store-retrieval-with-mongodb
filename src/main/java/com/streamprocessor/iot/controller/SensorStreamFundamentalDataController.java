package com.streamprocessor.iot.controller;


import com.streamprocessor.iot.constants.GlobalVariables;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@EnableCaching
@RequestMapping("/api/v1")
@Api(value = "Sensor's Fundamental Data", tags = "REST API for the Concrete fields of sensors.")
public class SensorStreamFundamentalDataController {

    @GetMapping("/sensors")
    @ApiOperation(value = "Get a list of all sensors.")
    @ApiResponse(code = 200, message = "OK", response = List.class)
    public ResponseEntity<List<String>> getSensorList() {
        List<String> sensorList = Arrays.asList(GlobalVariables.SENSOR_NAMES);
        return ResponseEntity.ok(sensorList);
    }
}
