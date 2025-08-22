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
    @PostMapping("/elog")
    public ResponseEntity<?> save(HttpServletRequest httpReq, @RequestBody PostExerciseLogReq req) {
        int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        log.info("logginedMemberId={}", logginedMemberId);
        log.info("req: {}", req);
        int result = exerciseLogService.saveExerciseLog(req, logginedMemberId);
        return ResponseEntity.ok(result);
    }

//    운동기록 상세조회
    @GetMapping("elog/{exerciselogId}")
    public ResponseEntity<?> getDetail(HttpServletRequest httpReq, @PathVariable int exerciselogId) {
        int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        GetExerciseLogDetailReq req = GetExerciseLogDetailReq.builder()
                .exerciselogId(exerciselogId)
                .memberId(logginedMemberId)
                .build();
        log.info("req:{}", req);
        GetExerciseLogDetailRes result = exerciseLogService.findByExerciselogId(req);
    return ResponseEntity.ok(result);
    }


//  운동기록 조회
    @GetMapping("/elog")
    public ResponseEntity<?> getAll(HttpServletRequest httpReq) {
        int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        List<GetExerciseLogRes> result = exerciseLogService.getExerciseLogAll(logginedMemberId);
        return ResponseEntity.ok(result);
    }

    // 페이징
    @GetMapping("/elog/list")
    public ResponseEntity<?> getExerciseLogList(HttpServletRequest httpReq, @ModelAttribute GetExerciseLogReq req) {
        int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        log.info("req:{}", req);
        List<GetExerciseLogRes> result = exerciseLogService.getExerciseLogList(logginedMemberId, req);
        log.info("exerciseLogList_result:{}", result);
        return ResponseEntity.ok(result);
    }

// 달력(년, 월)
@GetMapping("/elog/calendar")
public ResponseEntity<?> getEexerciselogCalendar(HttpServletRequest httpReq, @ModelAttribute ExerciseLogCalendarGetReq req) {
    int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);

    List<ExerciseLogCalendarGetRes> result = exerciseLogService.getExerciseLogDate(logginedMemberId, req);
    log.info("result:{}", result);
    return ResponseEntity.ok(result);
}


//    운동종목
    @GetMapping
    public ResponseEntity<List<GetExerciseRes>> getAllExercise() {
        List<GetExerciseRes> result = exerciseLogService.findAllExercise();
        return ResponseEntity.ok(result);
    }


//    운동기록 수정
//    @PutMapping("/elog")
//    public ResponseEntity<?> update(HttpServletRequest httpReq, @RequestBody PutExerciseLogReq req) {
//        int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
//        log.info("req:{}", req);
//        int result = exerciseLogService.modifyByExerciselogId(req, logginedMemberId);
//        log.info("result:{}", result);
//        return ResponseEntity.ok(result);
//    }

//    운동기록 삭제
    @DeleteMapping("/elog")
    public ResponseEntity<?> delete(HttpServletRequest httpReq, @RequestParam("exerciselog_id") int exerciselogId) {
        int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        GetExerciseLogDetailReq req = GetExerciseLogDetailReq.builder()
                .exerciselogId(exerciselogId)
                .memberId(logginedMemberId)
                .build();
        int result = exerciseLogService.deleteByExerciselogId(req);
        return ResponseEntity.ok(result);
    }

}
