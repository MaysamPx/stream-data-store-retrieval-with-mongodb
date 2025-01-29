package com.streamprocessor.iot.exceptions;

public class SqlInjectionThreatException extends Exception{
    private static final long serialVersionUID = 809595704303781222L;
    private static final String message = "Your passed parameter is not a valid due to SQL injection threat!";
    public SqlInjectionThreatException(String parameter) {

        this.parameter = parameter;
    }

    private final String parameter;

    @Override
    public String getMessage() {
        return message;
    }

    public String getSensor(){
        return parameter;
    }


}
