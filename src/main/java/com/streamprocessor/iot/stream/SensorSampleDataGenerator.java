package com.streamprocessor.iot.stream;

import com.streamprocessor.iot.constants.GlobalVariables;
import com.streamprocessor.iot.model.MetricType;
import com.streamprocessor.iot.model.SensorDataDTO;
import com.streamprocessor.iot.model.SensorType;
import com.streamprocessor.iot.service.SensorDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Component
public class SensorSampleDataGenerator {

    private static final Logger logger = LoggerFactory.getLogger(SensorSampleDataGenerator.class);


    @Autowired
    private SensorDataService sensorDataService;

    private static final String[] SENSOR_NAMES = GlobalVariables.SENSOR_NAMES;
    private static final Random RANDOM = new Random();

    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors()); //NOPMD - suppressed DoNotUseThreads - TODO explain reason for suppression
    ScheduledFuture<?> task;

    public SensorSampleDataGenerator(SensorDataService sensorDataService ) {
        this.sensorDataService = sensorDataService;
    }

    private void generateData() {
        String sensorName = SENSOR_NAMES[RANDOM.nextInt(SENSOR_NAMES.length)];
        SensorType type = generateSensorType(sensorName);
        MetricType metric = generateMetricType(type);
        Double metricValue = generateMetricValue(metric);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String timestamp = LocalDateTime.now().format(format);

        SensorDataDTO sensorData = SensorDataDTO.builder()
                .sensorName(sensorName)
                .sensorType(type)
                .metricType(metric)
                .metricValue(metricValue)
                .reportTimestamp(timestamp)
                .build();


        sensorDataService.saveSensorData(sensorData);
    }

    private SensorType generateSensorType(String sensorName) {
        switch (sensorName) {
            case "Sensor_1":
                return SensorType.THERMOSTAT;
            case "Sensor_2":
                return SensorType.FUEL_READING;
            case "Sensor_3":
                return SensorType.HEART_RATE_METER;
            default:
                return null;
        }
    }

    private MetricType generateMetricType(SensorType sensorType) {
        switch (sensorType) {
            case HEART_RATE_METER:
                return MetricType.HEART_RATE;
            case FUEL_READING:
                return MetricType.FUEL_LEVEL;
            case THERMOSTAT:
                return MetricType.TEMPERATURE;
            default:
                return null;
        }
    }

    private Double generateMetricValue(MetricType metric) {
        switch (metric) {
            case TEMPERATURE:
                return Double.valueOf(generateTemperature());
            case HEART_RATE:
                return Double.valueOf(generateHeartRate());
            case FUEL_LEVEL:
                return Double.valueOf(generateFuelLevel());
            default:
                return null;
        }
    }

    private double generateTemperature() {
        // Random value between 0 and 100 degrees Celsius
        return RANDOM.nextDouble() * 100;
    }

    private int generateHeartRate() {
        // Random value between 60 and 120 beats per minute
        return RANDOM.nextInt(61) + 60;
    }

    private int generateFuelLevel() {
        // Random value between 0 and 100
        return RANDOM.nextInt(101);
    }

    public synchronized void startStreamGenerator(){
        stopStreamGenerator();

        task = scheduledExecutorService.scheduleAtFixedRate(
                this::generateData, 0, 1000, TimeUnit.MILLISECONDS);

        logger.info("Stream Data Generator task scheduled successfully.");
    }

    public synchronized void stopStreamGenerator(){
        if(task != null){
            task.cancel(false);
            logger.info("Stream Data Generator task stopped successfully.");
        }
    }

}
