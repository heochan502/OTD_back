package com.otd.onetoday_back.health;

import com.otd.onetoday_back.health.model.GetExerciseLogDetailRes;
import com.otd.onetoday_back.health.model.PostExerciseLogReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExerciseLogMapper {
    int saveExerciseLog(PostExerciseLogReq req);
    GetExerciseLogDetailRes findByExerciseId(int exerciseLogId);
}
