package com.streamprocessor.iot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SensorDataDTO {
    private String sensorName;
    private SensorType sensorType;
    private MetricType metricType;
    private double metricValue;
    private String reportTimestamp;
}
