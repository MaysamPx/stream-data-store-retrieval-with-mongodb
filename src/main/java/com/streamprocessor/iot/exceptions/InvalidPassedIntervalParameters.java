package com.streamprocessor.iot.exceptions;

public class InvalidPassedIntervalParameters extends Exception{
    private static final long serialVersionUID = -1597089847772710706L;
    private static String message = "The given parameters are not valid! The LocalDateTime values expected.";
    public InvalidPassedIntervalParameters() {
    }

    @Override
    public String getMessage() {
        return message;
    }

}
