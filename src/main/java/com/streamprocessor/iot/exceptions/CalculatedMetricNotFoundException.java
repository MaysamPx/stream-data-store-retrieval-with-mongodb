package com.streamprocessor.iot.exceptions;

public class CalculatedMetricNotFoundException extends Exception {
    private static final long serialVersionUID = 7505030197259184857L;
    private static String message = "No maximum metric value found in result!";

    @Override
    public String getMessage() {
        return message;
    }
}
