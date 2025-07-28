package com.otd.onetoday_back.common.config;

import com.otd.onetoday_back.common.model.CustomException;
import com.otd.onetoday_back.common.model.ResultResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResultResponse<String>> handleCustomException(CustomException ex, HttpServletRequest request) {
        ex.printStackTrace(); // 로그 출력 (디버깅용)
        return ResponseEntity
                .status(ex.getStatus())
                .body(ResultResponse.failure(ex.getMessage(), ex.getStatus(), request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultResponse<String>> handleGeneralException(Exception ex, HttpServletRequest request) {
        ex.printStackTrace(); // 예외 로그 출력
        return ResponseEntity
                .status(500)
                .body(ResultResponse.failure("서버 내부 오류", 500, request.getRequestURI()));
    }
}