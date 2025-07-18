package com.otd.onetoday_back.health;

import com.otd.onetoday_back.health.model.GetExerciseLogDetailRes;
import com.otd.onetoday_back.health.model.GetExerciseLogRes;
import com.otd.onetoday_back.health.model.PostExerciseLogReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExerciseLogService {
    private final ExerciseLogMapper exerciseLogMapper;

//    운동 기록 저장
    public int saveExerciseLog(PostExerciseLogReq req) {
        return exerciseLogMapper.saveExerciseLog(req);
    }

//  운동기록 상세조회
    public GetExerciseLogDetailRes findByExerciseId(int exerciseLogId) {
        return exerciseLogMapper.findByExerciseId(exerciseLogId);
    }

//    운동기록 목록조회
    public List<GetExerciseLogRes> findAllByMemberNoOrderByExerciselogIdDesc(int memberNo) {
        return exerciseLogMapper.findAllByMemberNoOrderByExerciselogIdDesc(memberNo);
    }

}
