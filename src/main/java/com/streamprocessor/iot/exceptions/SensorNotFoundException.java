package com.streamprocessor.iot.exceptions;

public class SensorNotFoundException extends Exception{
    private static final long serialVersionUID = 2484522059083612782L;
    private static String message = "Your passed sensorName is not a valid sensor!";
    public SensorNotFoundException(String sensorName) {

        this.sensorName = sensorName;
    }

    private final String sensorName;

    @Override
    public String getMessage() {
        return message;
    }

    public String getSensor(){
        return sensorName;
    }

}
