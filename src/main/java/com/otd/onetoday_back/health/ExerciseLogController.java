package com.otd.onetoday_back.health;

import com.otd.onetoday_back.health.model.GetExerciseLogDetailRes;
import com.otd.onetoday_back.health.model.GetExerciseLogRes;
import com.otd.onetoday_back.health.model.PostExerciseLogReq;
import com.otd.onetoday_back.health.model.PutExerciseLogReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/otd/health/elog")
public class ExerciseLogController {
    private final ExerciseLogService exerciseLogService;

    //    운동기록 생성
    @PostMapping
    public ResponseEntity<Integer> save(@RequestBody PostExerciseLogReq req) {
        log.info("req: {}", req);
        int result = exerciseLogService.saveExerciseLog(req);
        return ResponseEntity.ok(result);
    }

//    운동기록 상세조회
    @GetMapping("{exerciseLogId}")
    public ResponseEntity<GetExerciseLogDetailRes> getDetail(@PathVariable int exerciseLogId) {
        GetExerciseLogDetailRes result = exerciseLogService.findByExerciselogId(exerciseLogId);
    return ResponseEntity.ok(result);
    }

//    운동기록 목록조회
    @GetMapping
    public ResponseEntity<List<GetExerciseLogRes>> getAll(@RequestParam("member_no") int memberNo) {
        log.info("memberNo: {}", memberNo);
        List<GetExerciseLogRes> result = exerciseLogService.findAllByMemberNoOrderByExerciselogIdDesc(memberNo);
        return ResponseEntity.ok(result);
    }

//    운동기록 수정
    @PutMapping
    public ResponseEntity<Integer> update(@RequestBody PutExerciseLogReq req) {
        log.info("req: {}", req);
        int result = exerciseLogService.modifyByExerciselogId(req);
        return ResponseEntity.ok(result);
    }

//    운동기록 삭제
    @DeleteMapping
    public ResponseEntity<Integer> delete(@RequestParam("exerciselog_id") int exerciselogId) {
        log.info("exerciseLogId: {}", exerciselogId);
        int result = exerciseLogService.deleteByExerciselogId(exerciselogId);
        return ResponseEntity.ok(result);
    }

}
