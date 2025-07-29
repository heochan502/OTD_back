package com.otd.onetoday_back.health;

import com.otd.onetoday_back.health.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExerciseLogMapper {
    int saveExerciseLog(PostExerciseLogDto dto);  // 운동기록생성
    GetExerciseLogDetailRes findByExerciselogId(GetExerciseLogDetailReq req);  // 운동기록상세조회
    List<GetExerciseLogRes> findAllByMemberIdOrderByExerciseDatetimeDesc(int memberNo);
    List<GetExerciseRes> findAllByExercise();
    int modifyByExerciselogId(PutExerciseLogDto dto);
    int deleteByExerciselogId(GetExerciseLogDetailReq req);
}
