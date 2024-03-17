package com.streamprocessor.iot.repository;

import com.streamprocessor.iot.model.SensorDataDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorDataRepository extends MongoRepository<SensorDataDTO, String> {
    List<SensorDataDTO> findBySensorNameAndReportTimestampBetween(String sensorName, String startDate, String endDate);
}