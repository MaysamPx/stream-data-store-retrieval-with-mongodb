package com.streamprocessor.iot.exceptions;

import java.io.Serial;

public class ErrorInCalculatingMetric extends Exception{
    @Serial
    private static final long serialVersionUID = 7761273740469393101L;
    private static final String MESSAGE = "An error occurred in calculating the requested metric!";


    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
