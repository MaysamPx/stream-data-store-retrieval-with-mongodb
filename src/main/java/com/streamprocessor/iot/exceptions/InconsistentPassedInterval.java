package com.streamprocessor.iot.exceptions;

public class InconsistentPassedInterval extends Exception{
    private static final long serialVersionUID = 6750785911062124732L;
    private static String message = "The given interval (start -> end) is not a consistent range!";
    public InconsistentPassedInterval() {
    }

    @Override
    public String getMessage() {
        return message;
    }

}
