package com.streamprocessor.iot.util;

import com.streamprocessor.iot.constants.GlobalVariables;
import com.streamprocessor.iot.exceptions.SensorNotFoundException;

public class ParameterValidator {

    public static boolean isValidSensorName(String sensorName) throws SensorNotFoundException {
        for (String validSensorName : GlobalVariables.SENSOR_NAMES) {
            if (validSensorName.equalsIgnoreCase(sensorName)) {
                return true;
            }
        }
        throw new SensorNotFoundException(sensorName);
    }

}
