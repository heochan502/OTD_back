package com.otd.onetoday_back.health;

import com.otd.onetoday_back.health.model.GetExerciseLogDetailReq;
import com.otd.onetoday_back.health.model.GetExerciseLogDetailRes;
import com.otd.onetoday_back.health.model.PostExerciseLogReq;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/otd/health")
public class ExerciseLogController {
    private final ExerciseLogService exerciseLogService;

    //    운동기록 생성
    @PostMapping("elog")
    public ResponseEntity<Integer> save(@RequestBody PostExerciseLogReq req) {
        log.info("req: {}", req);
        int result = exerciseLogService.saveExerciseLog(req);
        return ResponseEntity.ok(result);
    }

//    운동상세보기
    @GetMapping("elog/{exerciselogId}")
    public ResponseEntity<GetExerciseLogDetailRes> getDetail(@PathVariable GetExerciseLogDetailReq req) {
        log.info("req: {}", req);
        GetExerciseLogDetailRes result = exerciseLogService.getExerciseLogDetail(req);
return ResponseEntity.ok(result);
    }

}
