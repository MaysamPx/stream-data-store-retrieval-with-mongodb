package com.streamprocessor.iot.exceptions;

import java.io.Serial;

public class CalculatedMetricNotFoundException extends Exception {
    @Serial
    private static final long serialVersionUID = 7505030197259184857L;
    private static final String MESSAGE = "No maximum metric value found in result!";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
