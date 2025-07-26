package com.otd.onetoday_back.memo.config.model;

import com.otd.onetoday_back.memo.config.model.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ 모든 일반 Exception 처리
    @ExceptionHandler(Exception.class)
    public ResultResponse<Object> handleException(Exception e) {
        log.error("❗예외 발생: {}", e.getMessage(), e);
        return ResultResponse.fail("서버 내부 오류가 발생했습니다.", null, 500);
    }

    // ✅ 런타임 예외
    @ExceptionHandler(RuntimeException.class)
    public ResultResponse<Object> handleRuntimeException(RuntimeException e) {
        log.error("❗런타임 오류: {}", e.getMessage(), e);
        return ResultResponse.fail("알 수 없는 오류가 발생했습니다.", null, 500);
    }

    // ✅ 널 포인터 예외
    @ExceptionHandler(NullPointerException.class)
    public ResultResponse<Object> handleNullPointer(NullPointerException e) {
        log.warn("❗NullPointer 발생: {}", e.getMessage());
        return ResultResponse.fail("처리 중 Null 데이터가 감지되었습니다.", null, 500);
    }

    // ✅ 요청 파라미터 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultResponse<Object> handleValidation(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResultResponse.fail("유효성 검증 실패: " + errorMsg, null, 400);
    }

    // ✅ 커스텀 예외 처리 (옵션)
    @ExceptionHandler(CustomException.class)
    public ResultResponse<Object> handleCustom(CustomException e) {
        log.warn("❗CustomException: {}", e.getMessage());
        return ResultResponse.fail(e.getMessage(), null, e.getStatus());
    }
}