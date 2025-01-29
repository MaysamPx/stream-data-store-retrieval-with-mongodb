package com.streamprocessor.iot.controller;

import com.streamprocessor.iot.exceptions.*;
import com.streamprocessor.iot.model.APIRequestInterval;
import com.streamprocessor.iot.model.SensorDataDTO;
import com.streamprocessor.iot.service.SensorDataService;
import com.streamprocessor.iot.util.DateTimeUtil;
import com.streamprocessor.iot.util.ParameterValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@EnableCaching
@RequestMapping("/api/v1")
@Tag(name = "Sensor Data", description = "REST APIs for Sensor Data operations including retrieval of min, max, average, and median values.")
public class SensorStreamDataController {

    private final SensorDataService sensorDataService;

    public SensorStreamDataController(SensorDataService sensorDataService) {
        this.sensorDataService = sensorDataService;
    }

    @Operation(summary = "Get sensor data", description = "Retrieve a list of sensor data for a given sensor name, with optional start and end time parameters. Returns a pageable list.")
    @ApiResponse(responseCode = "200", description = "Sensor data retrieved successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @ApiResponse(responseCode = "401", description = "Unauthorized request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/{sensorName}")
    public ResponseEntity<Page<SensorDataDTO>> getSensorData(
            @Parameter(description = "Name of the sensor") @PathVariable String sensorName,
            @Parameter(description = "Start time in ISO 8601 format") @RequestParam(required = false) String start,
            @Parameter(description = "End time in ISO 8601 format") @RequestParam(required = false) String end,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") Integer size)
            throws InconsistentPassedInterval, InvalidPassedIntervalParameters {

        APIRequestInterval requestInterval = DateTimeUtil.getRequestInterval(start, end);

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<SensorDataDTO> sensorDataPage = null;

        return ResponseEntity.ok(sensorDataPage);
    }

    @Operation(summary = "Get minimum sensor value", description = "Retrieve the minimum sensor value for a given sensor name, with optional start and end time parameters.")
    @ApiResponse(responseCode = "200", description = "Minimum sensor value retrieved successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Double.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @ApiResponse(responseCode = "401", description = "Unauthorized request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/{sensorName}/min")
    public ResponseEntity<?> getMinSensorValue(
            @Parameter(description = "Name of the sensor") @PathVariable String sensorName,
            @Parameter(description = "Start time in ISO 8601 format") @RequestParam String start,
            @Parameter(description = "End time in ISO 8601 format") @RequestParam String end)
            throws InconsistentPassedInterval, InvalidPassedIntervalParameters, InterruptedException, ErrorInCalculatingMetric, IOException, URISyntaxException {

        try {
            ParameterValidator.isValidSensorName(sensorName);
            APIRequestInterval requestInterval = DateTimeUtil.getRequestInterval(start, end);
            double minValue = sensorDataService.getSensorDataMinValue(sensorName, requestInterval.getStart(), requestInterval.getEnd());
            return ResponseEntity.ok(minValue);
        } catch (InconsistentPassedInterval e) {
            String errorMessage = "Invalid request parameters: " + e.getMessage();
            return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
        } catch (SensorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get maximum sensor value", description = "Retrieve the maximum sensor value for a given sensor name, with optional start and end time parameters.")
    @ApiResponse(responseCode = "200", description = "Maximum sensor value retrieved successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Double.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @ApiResponse(responseCode = "401", description = "Unauthorized request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/{sensorName}/max")
    public ResponseEntity<?> getMaxSensorValue(
            @Parameter(description = "Name of the sensor") @PathVariable String sensorName,
            @Parameter(description = "Start time in ISO 8601 format") @RequestParam String start,
            @Parameter(description = "End time in ISO 8601 format") @RequestParam String end)
            throws InconsistentPassedInterval, InvalidPassedIntervalParameters, InterruptedException, ErrorInCalculatingMetric, IOException, CalculatedMetricNotFoundException {

        try {
            ParameterValidator.isValidSensorName(sensorName);
            APIRequestInterval requestInterval = DateTimeUtil.getRequestInterval(start, end);
            double maxValue = sensorDataService.getSensorDataMaxValue(sensorName, requestInterval.getStart(), requestInterval.getEnd());
            return ResponseEntity.ok(maxValue);
        } catch (InconsistentPassedInterval e) {
            String errorMessage = "Invalid request parameters: " + e.getMessage();
            return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
        } catch (SensorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get average sensor value", description = "Retrieve the average sensor value for a given sensor name, with optional start and end time parameters.")
    @ApiResponse(responseCode = "200", description = "Average sensor value retrieved successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Double.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @ApiResponse(responseCode = "401", description = "Unauthorized request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/{sensorName}/average")
    public ResponseEntity<?> getAverageSensorValue(
            @Parameter(description = "Name of the sensor") @PathVariable String sensorName,
            @Parameter(description = "Start time in ISO 8601 format") @RequestParam String start,
            @Parameter(description = "End time in ISO 8601 format") @RequestParam String end)
            throws InconsistentPassedInterval, InvalidPassedIntervalParameters, InterruptedException, ErrorInCalculatingMetric, IOException {

        try {
            ParameterValidator.isValidSensorName(sensorName);
            APIRequestInterval requestInterval = DateTimeUtil.getRequestInterval(start, end);
            double averageValue = sensorDataService.getSensorDataAvgValue(sensorName, requestInterval.getStart(), requestInterval.getEnd());
            return ResponseEntity.ok(averageValue);
        } catch (InconsistentPassedInterval e) {
            String errorMessage = "Invalid request parameters: " + e.getMessage();
            return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
        } catch (SensorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get median sensor value", description = "Retrieve the median sensor value for a given sensor name, with optional start and end time parameters.")
    @ApiResponse(responseCode = "200", description = "Median sensor value retrieved successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Double.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @ApiResponse(responseCode = "401", description = "Unauthorized request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/{sensorName}/median")
    public ResponseEntity<?> getMedianSensorValue(
            @Parameter(description = "Name of the sensor") @PathVariable String sensorName,
            @Parameter(description = "Start time in ISO 8601 format") @RequestParam String start,
            @Parameter(description = "End time in ISO 8601 format") @RequestParam String end)
            throws InconsistentPassedInterval, InvalidPassedIntervalParameters, InterruptedException, ErrorInCalculatingMetric, IOException {

        try {
            ParameterValidator.isValidSensorName(sensorName);
            APIRequestInterval requestInterval = DateTimeUtil.getRequestInterval(start, end);
            double medianValue = sensorDataService.getSensorDataMedianValue(sensorName, requestInterval.getStart(), requestInterval.getEnd());
            return ResponseEntity.ok(medianValue);
        } catch (InconsistentPassedInterval e) {
            String errorMessage = "Invalid request parameters: " + e.getMessage();
            return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
        } catch (SensorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    public static class ErrorResponse {
        private String errorMessage;

        public ErrorResponse(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}
