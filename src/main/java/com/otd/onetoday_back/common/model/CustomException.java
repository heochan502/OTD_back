package com.otd.onetoday_back.common.model;

public class CustomException extends RuntimeException {

    private final int status;

    public CustomException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}