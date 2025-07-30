package com.otd.onetoday_back.common.config;

import com.otd.onetoday_back.common.model.CustomException;
import com.otd.onetoday_back.common.model.ResultResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ 사용자 정의 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResultResponse<String>> handleCustomException(CustomException ex, HttpServletRequest request) {
        log.warn("CustomException 발생: {} (status: {})", ex.getMessage(), ex.getStatus());
        return ResponseEntity
                .status(ex.getStatus())
                .body(ResultResponse.failure(ex.getMessage(), ex.getStatus(), request.getRequestURI()));
    }

    // ✅ 일반 예외 처리 (NullPointerException 등)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultResponse<String>> handleGeneralException(Exception ex, HttpServletRequest request) {
        log.error("예기치 못한 오류 발생: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(500)
                .body(ResultResponse.failure("서버 내부 오류가 발생했습니다.", 500, request.getRequestURI()));
    }
}