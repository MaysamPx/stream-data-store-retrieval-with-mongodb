package com.streamprocessor.iot.controller;


import com.streamprocessor.iot.exceptions.*;
import com.streamprocessor.iot.model.APIRequestInterval;
import com.streamprocessor.iot.model.SensorDataDTO;
import com.streamprocessor.iot.service.SensorDataService;
import com.streamprocessor.iot.util.DateTimeUtil;
import com.streamprocessor.iot.util.ParameterValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "SensorData", tags = "Sensor's Data REST APIs (Sensor Data/Min/Max/Avg)")
public class SensorStreamDataController {

    private final SensorDataService sensorDataService;

    public SensorStreamDataController(SensorDataService sensorDataService) {
        this.sensorDataService = sensorDataService;
    }

    @ApiOperation(value = "Get sensor data", notes = "Retrieve a list of sensor data for a given sensor name, with optional start and end time parameters. Returns a pageable list.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sensor data retrieved successfully"),
            @ApiResponse(code = 400, message = "Invalid request parameters"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/{sensorName}")
    public ResponseEntity<Page<SensorDataDTO>> getSensorData(@PathVariable String sensorName,
                                                             @RequestParam(required = false) String start,
                                                             @RequestParam(required = false) String end,
                                                             @RequestParam(defaultValue = "0") Integer page,
                                                             @RequestParam(defaultValue = "10") Integer size) throws InconsistentPassedInterval, InvalidPassedIntervalParameters {

        APIRequestInterval requestInterval = DateTimeUtil.getRequestInterval(start, end);

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<SensorDataDTO> sensorDataPage = null;

        return ResponseEntity.ok(sensorDataPage);
    }

    @ApiOperation(value = "Get minimum sensor value", notes = "Retrieve the minimum sensor value for a given sensor name, with optional start and end time parameters.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Minimum sensor value retrieved successfully"),
            @ApiResponse(code = 400, message = "Invalid request parameters"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/{sensorName}/min")
    public ResponseEntity<?> getMinSensorValue(@PathVariable String sensorName,
                                                    @RequestParam(required = true) String start,
                                                    @RequestParam(required = true) String end) throws InconsistentPassedInterval, InvalidPassedIntervalParameters, InterruptedException, ErrorInCalculatingMetric, IOException, URISyntaxException {
        try {
            ParameterValidator.isValidSensorName(sensorName);

            APIRequestInterval requestInterval = DateTimeUtil.getRequestInterval(start, end);
            double minValue = sensorDataService.getSensorDataMinValue(sensorName, requestInterval.getStart(), requestInterval.getEnd());

            return ResponseEntity.ok(minValue);
        } catch (InconsistentPassedInterval | InvalidPassedIntervalParameters e) {
            String errorMessage = "Invalid request parameters: " + e.getMessage();
            return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
        } catch (SensorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Get maximum sensor value", notes = "Retrieve the maximum sensor value for a given sensor name, with optional start and end time parameters.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Maximum sensor value retrieved successfully"),
            @ApiResponse(code = 400, message = "Invalid request parameters"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/{sensorName}/max")
    public ResponseEntity<?> getMaxSensorValue(@PathVariable String sensorName,
                                                    @RequestParam(required = true) String start,
                                                    @RequestParam(required = true) String end) throws InconsistentPassedInterval, InvalidPassedIntervalParameters, InterruptedException, ErrorInCalculatingMetric, IOException, CalculatedMetricNotFoundException {
        try{
            ParameterValidator.isValidSensorName(sensorName);
            APIRequestInterval requestInterval = DateTimeUtil.getRequestInterval(start, end);
            double maxValue =  sensorDataService.getSensorDataMaxValue(sensorName, requestInterval.getStart(), requestInterval.getEnd());

            return ResponseEntity.ok(maxValue);

        } catch (InconsistentPassedInterval | InvalidPassedIntervalParameters e) {
            String errorMessage = "Invalid request parameters: " + e.getMessage();
            return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
        } catch (SensorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }


    @ApiOperation(value = "Get average sensor value", notes = "Retrieve the average sensor value for a given sensor name, with optional start and end time parameters.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Median sensor value retrieved successfully"),
            @ApiResponse(code = 400, message = "Invalid request parameters"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/{sensorName}/average")
    public ResponseEntity<?> getAverageSensorValue(@PathVariable String sensorName,
                                                       @RequestParam(required = true) String start,
                                                       @RequestParam(required = true) String end) throws InconsistentPassedInterval, InvalidPassedIntervalParameters, InterruptedException, ErrorInCalculatingMetric, IOException {
        try{
            ParameterValidator.isValidSensorName(sensorName);
            APIRequestInterval requestInterval = DateTimeUtil.getRequestInterval(start, end);
            double averageValue = sensorDataService.getSensorDataAvgValue(sensorName, requestInterval.getStart(), requestInterval.getEnd());

            return ResponseEntity.ok(averageValue);

        } catch (InconsistentPassedInterval | InvalidPassedIntervalParameters e) {
            String errorMessage = "Invalid request parameters: " + e.getMessage();
            return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
        } catch (SensorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/{sensorName}/median")
    public ResponseEntity<?> getMedianSensorValue(@PathVariable String sensorName,
                                                   @RequestParam(required = true) String start,
                                                   @RequestParam(required = true) String end) throws InconsistentPassedInterval, InvalidPassedIntervalParameters, InterruptedException, ErrorInCalculatingMetric, IOException {
        try{
            ParameterValidator.isValidSensorName(sensorName);
            APIRequestInterval requestInterval = DateTimeUtil.getRequestInterval(start, end);
            double medianValue = sensorDataService.getSensorDataMedianValue(sensorName, requestInterval.getStart(), requestInterval.getEnd());

            return ResponseEntity.ok(medianValue);

        } catch (InconsistentPassedInterval | InvalidPassedIntervalParameters e) {
            String errorMessage = "Invalid request parameters: " + e.getMessage();
            return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
        } catch (SensorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    public class ErrorResponse {
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