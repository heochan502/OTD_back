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

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResultResponse<?>> handleCustomException(
            CustomException e,
            HttpServletRequest request
    ) {
        log.error("CustomException: {}", e.getMessage());
        return ResponseEntity
                .status(e.getStatusCode())
                .body(ResultResponse.failure(
                        e.getMessage(),
                        e.getStatusCode(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultResponse<?>> handleGeneralException(
            Exception e,
            HttpServletRequest request
    ) {
        log.error("Unhandled Exception: ", e);
        return ResponseEntity
                .status(500)
                .body(ResultResponse.failure(
                        "서버 오류가 발생했습니다.",
                        500,
                        request.getRequestURI()
                ));
    }
}