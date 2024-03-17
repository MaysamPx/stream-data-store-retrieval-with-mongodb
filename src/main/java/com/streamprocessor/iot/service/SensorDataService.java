package com.streamprocessor.iot.service;

import com.streamprocessor.iot.model.SensorDataDTO;
import com.streamprocessor.iot.repository.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorDataService {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    public void saveSensorData(SensorDataDTO sensorDataDTO) {
       sensorDataRepository.save(sensorDataDTO);
    }

    public Double getSensorDataMinValue(String sensorName, String startDate, String endDate) {
        List<SensorDataDTO> sensorDataList = sensorDataRepository.findBySensorNameAndReportTimestampBetween(sensorName, startDate, endDate);
        double minValue = sensorDataList.stream().mapToDouble(SensorDataDTO::getMetricValue).min().orElse(0);

        return minValue;
    }

    public Double getSensorDataMaxValue(String sensorName, String startDate, String endDate) {
        List<SensorDataDTO> sensorDataList = sensorDataRepository.findBySensorNameAndReportTimestampBetween(sensorName, startDate, endDate);
        double maxValue = sensorDataList.stream().mapToDouble(SensorDataDTO::getMetricValue).max().orElse(0);

        return maxValue;
    }

    public Double getSensorDataAvgValue(String sensorName, String startDate, String endDate) {
        List<SensorDataDTO> sensorDataList = sensorDataRepository.findBySensorNameAndReportTimestampBetween(sensorName, startDate, endDate);
        double avgValue = sensorDataList.stream().mapToDouble(SensorDataDTO::getMetricValue).average().orElse(0);

        return avgValue;
    }

    public Double getSensorDataMedianValue(String sensorName, String startDate, String endDate) {
        List<SensorDataDTO> sensorDataList = sensorDataRepository.findBySensorNameAndReportTimestampBetween(sensorName, startDate, endDate);

        double medianValue;
        List<Double> sortedValues = sensorDataList.stream().map(SensorDataDTO::getMetricValue).sorted().collect(Collectors.toList());
        if (sortedValues.size() % 2 == 0) {
            medianValue = (sortedValues.get(sortedValues.size() / 2 - 1) + sortedValues.get(sortedValues.size() / 2)) / 2;
        } else {
            medianValue = sortedValues.get(sortedValues.size() / 2);
        }

        return medianValue;
    }

}
