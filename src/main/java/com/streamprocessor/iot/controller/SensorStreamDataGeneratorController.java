package com.streamprocessor.iot.controller;

import com.streamprocessor.iot.stream.SensorSampleDataGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONObject;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableCaching
@RequestMapping("/api/v1")
@Tag(name = "Sensor Stream Data Generator", description = "REST APIs to start/stop the stream data generator")
public class SensorStreamDataGeneratorController {

    private final SensorSampleDataGenerator sensorSampleDataGenerator;

    public SensorStreamDataGeneratorController(SensorSampleDataGenerator sensorSampleDataGenerator) {
        this.sensorSampleDataGenerator = sensorSampleDataGenerator;
    }

    @GetMapping(value = "/generator/start", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Starts the sample stream data generator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Stream Data Generator started successfully!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred.",
                    content = @Content)
    })
    public ResponseEntity<String> startTask() {
        sensorSampleDataGenerator.startStreamGenerator();

        JSONObject response = new JSONObject();
        response.put("Status", "Succeeded");
        response.put("Message", "The Stream Data Generator started successfully!");

        return ResponseEntity.ok(response.toString());
    }

    @GetMapping(value = "/generator/stop", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Stops the sample stream data generator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Stream Data Generator stopped successfully!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred.",
                    content = @Content)
    })
    public ResponseEntity<String> stopTask() {
        sensorSampleDataGenerator.stopStreamGenerator();
        JSONObject response = new JSONObject();
        response.put("Status", "Succeeded");
        response.put("Message", "The Stream Data Generator stopped successfully!");

        return ResponseEntity.ok(response.toString());
    }
}