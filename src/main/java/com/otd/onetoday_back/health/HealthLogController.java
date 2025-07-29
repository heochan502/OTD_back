package com.otd.onetoday_back.health;


import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.common.util.HttpUtils;
import com.otd.onetoday_back.health.model.GetHealthLogDetailReq;
import com.otd.onetoday_back.health.model.GetHealthLogDetailRes;
import com.otd.onetoday_back.health.model.GetHealthLogRes;
import com.otd.onetoday_back.health.model.PostHealthLogReq;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/OTD/health/hlog")
public class HealthLogController {
    private final HealthLogService healthLogService;


    //    건강기록 저장
    @PostMapping
    public ResponseEntity<?> save(HttpServletRequest httpReq, @RequestBody PostHealthLogReq req) {
        int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        int result = healthLogService.saveHealthLog(req, logginedMemberId);
        return ResponseEntity.ok(result);
    }

    //    건강기록 상세조회
    @GetMapping("{healthlogId}")
    public ResponseEntity<?> getDetail(HttpServletRequest httpReq, @PathVariable int healthlogId) {
        int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        GetHealthLogDetailReq req = GetHealthLogDetailReq.builder()
                .healthlogId(healthlogId)
                .memberId(logginedMemberId)
                .build();
        GetHealthLogDetailRes result = healthLogService.findByHealthlogId(req);
        return ResponseEntity.ok(result);
    }

    //    건강기록 목록조회
    @GetMapping
    public ResponseEntity<?> getAll(HttpServletRequest httpReq) {
        int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        List<GetHealthLogRes> result = healthLogService.findAllByMemberIdOrderByHealthlogIdDesc(logginedMemberId);
        return ResponseEntity.ok(result);
    }

//    건강기록 삭제
    @DeleteMapping
    public ResponseEntity<?> delete(HttpServletRequest httpReq, @RequestParam("healthlog_id") int healthlogId) {
        int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        GetHealthLogDetailReq req = GetHealthLogDetailReq.builder()
                .memberId(logginedMemberId)
                .healthlogId(healthlogId)
                .build();

        int result = healthLogService.deleteByHealthlogId(req);
        return ResponseEntity.ok(result);
    }

}