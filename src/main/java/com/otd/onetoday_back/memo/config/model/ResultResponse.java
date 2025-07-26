package com.otd.onetoday_back.memo.config.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ResultResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private int status;
    private String timestamp;

    public ResultResponse(boolean success, String message, T data, int status) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.status = status;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static <T> ResultResponse<T> ok(String message, T data) {
        return new ResultResponse<>(true, message, data, 200);
    }

    public static <T> ResultResponse<T> fail(String message, T data, int status) {
        return new ResultResponse<>(false, message, data, status);
    }
}