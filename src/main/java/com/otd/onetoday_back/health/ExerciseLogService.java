package com.otd.onetoday_back.health;

import com.otd.onetoday_back.health.model.PostExerciseLogDto;
import com.otd.onetoday_back.health.model.PostExerciseLogReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExerciseLogService {
    private final ExerciseLogMapper exerciseLogMapper;

    public int saveExerciseLog(PostExerciseLogReq req) {
        return exerciseLogMapper.saveExerciseLog(req);
    }
}
