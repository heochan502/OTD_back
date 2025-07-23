package com.otd.onetoday_back.health;

import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.common.util.HttpUtils;
import com.otd.onetoday_back.health.model.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/OTD/health")
public class ExerciseLogController {
    private final ExerciseLogService exerciseLogService;

    //    운동기록 생성
    @PostMapping
    public ResponseEntity<Integer> save(HttpServletRequest httpReq, @RequestBody PostExerciseLogReq req) {
        int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        log.info("req: {}", req);
        int result = exerciseLogService.saveExerciseLog(req, logginedMemberId);
        return ResponseEntity.ok(result);
    }

//    운동기록 상세조회
    @GetMapping("/elog/{exerciseLogId}")
    public ResponseEntity<GetExerciseLogDetailRes> getDetail(HttpServletRequest httpReq, @PathVariable int exerciseLogId) {
        int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        GetExerciseLogDetailReq req = GetExerciseLogDetailReq.builder()
                .exerciselogId(exerciseLogId)
                .memberId(logginedMemberId)
                .build();
        log.info("req:{}", req);
        GetExerciseLogDetailRes result = exerciseLogService.findByExerciselogId(req);
    return ResponseEntity.ok(result);
    }

//    운동기록 목록조회
    @GetMapping("/elog")
    public ResponseEntity<List<GetExerciseLogRes>> getAll(HttpServletRequest httpReq) {
        int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        log.info("logginedMemberId:{}", logginedMemberId);
        List<GetExerciseLogRes> result = exerciseLogService.findAllByMemberIdOrderByExerciselogIdDesc(logginedMemberId);
        return ResponseEntity.ok(result);
    }

//    운동종목
    @GetMapping
    public ResponseEntity<List<GetExerciseRes>> getAllExercise() {
        List<GetExerciseRes> result = exerciseLogService.findAllExercise();
        return ResponseEntity.ok(result);
    }


//    운동기록 수정
    @PutMapping
    public ResponseEntity<Integer> update(HttpServletRequest httpReq, @RequestBody PutExerciseLogReq req) {
        int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        int result = exerciseLogService.modifyByExerciselogId(req, logginedMemberId);
        return ResponseEntity.ok(result);
    }

//    운동기록 삭제
    @DeleteMapping
    public ResponseEntity<Integer> delete(HttpServletRequest httpReq, @RequestParam("exerciselog_id") int exerciselogId) {
        int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        GetExerciseLogDetailReq req = GetExerciseLogDetailReq.builder()
                .exerciselogId(exerciselogId)
                .memberId(logginedMemberId)
                .build();

        int result = exerciseLogService.deleteByExerciselogId(req);
        return ResponseEntity.ok(result);
    }

}
