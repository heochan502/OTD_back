package com.otd.onetoday_back.health;

import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.common.util.HttpUtils;
import com.otd.onetoday_back.health.model.PostExerciseLogReq;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/otd/health")
public class ExerciseLogController {
    private final ExerciseLogService exerciseLogService;

    @PostMapping("elog")
    public ResponseEntity<Integer> saveExerciseLog(@RequestBody PostExerciseLogReq req){
        log.info("req: {}", req);
        int result = exerciseLogService.saveExerciseLog(req);
        return ResponseEntity.ok(result);
    }
}
