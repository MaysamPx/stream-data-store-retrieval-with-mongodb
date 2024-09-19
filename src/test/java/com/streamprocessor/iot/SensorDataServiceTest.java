package com.streamprocessor.iot;

import com.streamprocessor.iot.model.SensorDataDTO;
import com.streamprocessor.iot.repository.SensorDataRepository;
import com.streamprocessor.iot.service.SensorDataService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class SensorDataServiceTest {

    @Mock
    private SensorDataRepository sensorDataRepository;

    @InjectMocks
    private SensorDataService sensorDataService;

    private List<SensorDataDTO> sensorDataList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        sensorDataList = new ArrayList<>();
        SensorDataDTO sensorDataDTO1 = new SensorDataDTO();
        sensorDataDTO1.setSensorName("Sensor_1");
        sensorDataDTO1.setReportTimestamp("2022-04-14T01:00:00.111");
        sensorDataDTO1.setMetricValue(10.0);
        sensorDataList.add(sensorDataDTO1);

        SensorDataDTO sensorDataDTO2 = new SensorDataDTO();
        sensorDataDTO2.setSensorName("Sensor_1");
        sensorDataDTO2.setReportTimestamp("2022-04-14T01:00:00.112");
        sensorDataDTO2.setMetricValue(20.0);
        sensorDataList.add(sensorDataDTO2);

        SensorDataDTO sensorDataDTO3 = new SensorDataDTO();
        sensorDataDTO3.setSensorName("Sensor_1");
        sensorDataDTO3.setReportTimestamp("2022-04-14T01:00:00.113");
        sensorDataDTO3.setMetricValue(30.0);
        sensorDataList.add(sensorDataDTO3);
    }

    @Test
    void testSaveSensorData() {
        SensorDataDTO sensorDataDTO = new SensorDataDTO();
        sensorDataDTO.setSensorName("Sensor_1");
        sensorDataDTO.setReportTimestamp("2022-04-14T01:00:00.114");
        sensorDataDTO.setMetricValue(10.0);

        sensorDataService.saveSensorData(sensorDataDTO);

        verify(sensorDataRepository, times(1)).save(sensorDataDTO);
    }

    @Test
    void testGetSensorDataMinValue() {
        when(sensorDataRepository.findBySensorNameAndReportTimestampBetween("Sensor_1", "2021-04-14T01:00:00.112", "2024-04-14T01:00:00.112")).thenReturn(sensorDataList);

        double minValue = sensorDataService.getSensorDataMinValue("Sensor_1", "2021-04-14T01:00:00.112", "2024-04-14T01:00:00.112");

        Assertions.assertEquals(10.0, minValue);
    }

    @Test
    void testGetSensorDataMaxValue() {
        when(sensorDataRepository.findBySensorNameAndReportTimestampBetween("Sensor_1", "2021-04-14T01:00:00.112", "2024-04-14T01:00:00.112")).thenReturn(sensorDataList);

        double maxValue = sensorDataService.getSensorDataMaxValue("Sensor_1", "2021-04-14T01:00:00.112", "2024-04-14T01:00:00.112");

        Assertions.assertEquals(30.0, maxValue);
    }

    @Test
    void testGetSensorDataAvgValue() {
        when(sensorDataRepository.findBySensorNameAndReportTimestampBetween("Sensor_1", "2021-04-14T01:00:00.112", "2024-04-14T01:00:00.112")).thenReturn(sensorDataList);

        double avgValue = sensorDataService.getSensorDataAvgValue("Sensor_1", "2021-04-14T01:00:00.112", "2024-04-14T01:00:00.112");

        Assertions.assertEquals(20.0, avgValue);
    }

    @Test
    void testGetSensorDataMedianValue() {
        when(sensorDataRepository.findBySensorNameAndReportTimestampBetween("Sensor_1", "2021-04-14T01:00:00.112", "2024-04-14T01:00:00.112")).thenReturn(sensorDataList);
        double medianValue = sensorDataService.getSensorDataMedianValue("Sensor_1", "2021-04-14T01:00:00.112", "2024-04-14T01:00:00.112");

        Assertions.assertEquals(20.0, medianValue);
    }
}
