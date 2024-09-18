package com.streamprocessor.iot.exceptions;

import java.io.Serial;

public class InvalidPassedIntervalParameters extends Exception{
    @Serial
    private static final long serialVersionUID = -1597089847772710706L;
    private static final String MESSAGE = "The given parameters are not valid! The LocalDateTime values expected.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
