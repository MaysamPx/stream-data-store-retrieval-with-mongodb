package com.streamprocessor.iot.exceptions;

import java.io.Serial;

public class InconsistentPassedInterval extends Exception{
    @Serial
    private static final long serialVersionUID = 6750785911062124732L;
    private static final String MESSAGE = "The given interval (start -> end) is not a consistent range!";

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
