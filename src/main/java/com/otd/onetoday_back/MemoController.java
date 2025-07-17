package com.otd.onetoday_back;

import com.otd.onetoday_back.config.model.ResultResponse;
import com.otd.onetoday_back.model.MemoGetReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/otd/memo")
public class MemoController {
    private final MemoService memoService;
    public ResponseEntity<Integer> postMemo(@RequestBody MemoGetReq req) {
        log.info("req={}", req);
        int result = memoService.save(req);
        return new ResultResponse<>("메모 저장 성공", result);
    }


}
