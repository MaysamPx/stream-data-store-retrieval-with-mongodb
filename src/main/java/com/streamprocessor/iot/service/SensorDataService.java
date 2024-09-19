package com.streamprocessor.iot.service;

import com.streamprocessor.iot.model.SensorDataDTO;
import com.streamprocessor.iot.repository.SensorDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorDataService {

    private final SensorDataRepository sensorDataRepository;

    public SensorDataService(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    public void saveSensorData(SensorDataDTO sensorDataDTO) {
       sensorDataRepository.save(sensorDataDTO);
    }

    public Double getSensorDataMinValue(String sensorName, String startDate, String endDate) {
        List<SensorDataDTO> sensorDataList = sensorDataRepository.findBySensorNameAndReportTimestampBetween(sensorName, startDate, endDate);
        return sensorDataList.stream().mapToDouble(SensorDataDTO::getMetricValue).min().orElse(0);
    }

    public Double getSensorDataMaxValue(String sensorName, String startDate, String endDate) {
        List<SensorDataDTO> sensorDataList = sensorDataRepository.findBySensorNameAndReportTimestampBetween(sensorName, startDate, endDate);
        return sensorDataList.stream().mapToDouble(SensorDataDTO::getMetricValue).max().orElse(0);
    }

    public Double getSensorDataAvgValue(String sensorName, String startDate, String endDate) {
        List<SensorDataDTO> sensorDataList = sensorDataRepository.findBySensorNameAndReportTimestampBetween(sensorName, startDate, endDate);
        return sensorDataList.stream().mapToDouble(SensorDataDTO::getMetricValue).average().orElse(0);
    }

    public Double getSensorDataMedianValue(String sensorName, String startDate, String endDate) {
        List<SensorDataDTO> sensorDataList = sensorDataRepository.findBySensorNameAndReportTimestampBetween(sensorName, startDate, endDate);

        double medianValue;
        List<Double> sortedValues = sensorDataList.stream().map(SensorDataDTO::getMetricValue).sorted().toList();
        if (sortedValues.size() % 2 == 0) {
            medianValue = (sortedValues.get(sortedValues.size() / 2 - 1) + sortedValues.get(sortedValues.size() / 2)) / 2;
        } else {
            medianValue = sortedValues.get(sortedValues.size() / 2);
        }

        return medianValue;
    }

}
