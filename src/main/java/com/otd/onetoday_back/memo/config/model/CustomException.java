package com.otd.onetoday_back.memo.config.model;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final int status;

    public CustomException(String message, int status) {
        super(message);
        this.status = status;
    }
}