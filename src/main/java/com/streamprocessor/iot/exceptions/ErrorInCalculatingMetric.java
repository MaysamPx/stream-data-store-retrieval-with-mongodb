package com.streamprocessor.iot.exceptions;

public class ErrorInCalculatingMetric extends Exception{
    private static final long serialVersionUID = 7761273740469393101L;
    private static String message = "An error occurred in calculating the requested metric!";
    public ErrorInCalculatingMetric() {
    }

    @Override
    public String getMessage() {
        return message;
    }

}
