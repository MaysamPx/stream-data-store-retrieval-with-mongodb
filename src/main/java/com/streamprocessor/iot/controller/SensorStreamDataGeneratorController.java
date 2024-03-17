package com.streamprocessor.iot.controller;


import com.streamprocessor.iot.stream.SensorSampleDataGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableCaching
@RequestMapping("/api/v1")
@Api(value = "SensorStreamDataGenerator", tags = "REST APIs to start/stop the stream data generator")
public class SensorStreamDataGeneratorController {

    @Autowired
    private SensorSampleDataGenerator sensorSampleDataGenerator;

    @GetMapping("/generator/start")
    @ApiOperation(value = "Starts the sample stream data generator")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Stream Data Generator started successfully!"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 500, message = "Internal server error occurred.")
    })
    public ResponseEntity<String> startTask() {
        sensorSampleDataGenerator.startStreamGenerator();

        JSONObject response = new JSONObject();
        response.put("Status", "Succeeded");
        response.put("Message", "The Stream Data Generator started successfully!");

        return ResponseEntity.ok(response.toString());
    }

    @GetMapping("/generator/stop")
    @ApiOperation(value = "Stops the sample stream data generator")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Stream Data Generator stopped successfully!"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 500, message = "Internal server error occurred.")
    })
    public ResponseEntity<String> stopTask() {
        sensorSampleDataGenerator.stopStreamGenerator();
        JSONObject response = new JSONObject();
        response.put("Status", "Succeeded");
        response.put("Message", "The Stream Data Generator stopped successfully!");

        return ResponseEntity.ok(response.toString());
    }
}
