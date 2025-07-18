package com.otd.onetoday_back.health;

import com.otd.onetoday_back.health.model.GetExerciseLogDetailRes;
import com.otd.onetoday_back.health.model.GetExerciseLogRes;
import com.otd.onetoday_back.health.model.PostExerciseLogReq;
import com.otd.onetoday_back.health.model.PutExerciseLogReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExerciseLogMapper {
    int saveExerciseLog(PostExerciseLogReq req);  // 운동기록생성
    GetExerciseLogDetailRes findByExerciselogId(int exerciseLogId);  // 운동기록상세조회
    List<GetExerciseLogRes> findAllByMemberNoOrderByExerciselogIdDesc(int memberNo);
    int modifyByExerciselogId(PutExerciseLogReq req);
    int deleteByExerciselogId(int exerciselogId);
}
