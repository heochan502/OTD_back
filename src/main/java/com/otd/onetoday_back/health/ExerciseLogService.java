package com.otd.onetoday_back.health;

import com.otd.onetoday_back.health.model.*;
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
    public int saveExerciseLog(PostExerciseLogReq req, int logginedMemberId) {
        PostExerciseLogDto postExerciseLogDto = PostExerciseLogDto.builder()
                .exerciseId(req.getExerciseId())
                .exerciseKcal(req.getExerciseKcal())
                .exerciseDatetime(req.getExerciseDatetime())
                .exerciseDuration(req.getExerciseDuration())
                .effortLevel(req.getEffortLevel())
                .memberId(logginedMemberId)
                .build();

       return exerciseLogMapper.saveExerciseLog(postExerciseLogDto);

    }

//  운동기록 상세조회
    public GetExerciseLogDetailRes findByExerciselogId(GetExerciseLogDetailReq req) {
        return exerciseLogMapper.findByExerciselogId(req);
    }

//    운동기록 목록조회
    public List<GetExerciseLogRes> getExerciseLogList(GetExerciseLogDto dto) {
        return exerciseLogMapper.findAllByMemberIdOrderByExerciseDatetimeDesc(dto);
    }

//    운동종목
    public List<GetExerciseRes> findAllExercise() {
        return exerciseLogMapper.findAllByExercise();
    }
//    운동기록 수정
    public int modifyByExerciselogId(PutExerciseLogReq req, int logginedMemberId) {
        PutExerciseLogDto putExerciseLogDto = PutExerciseLogDto.builder()
                .exerciselogId(req.getExerciseId())
                .exerciseId(req.getExerciseId())
                .exerciseKcal(req.getExerciseKcal())
                .exerciseDatetime(req.getExerciseDatetime())
                .exerciseDuration(req.getExerciseDuration())
                .effortLevel(req.getEffortLevel())
                .memberId(logginedMemberId)
                .build();
        return exerciseLogMapper.modifyByExerciselogId(putExerciseLogDto);
    }

//    운동기록 삭제
    public int deleteByExerciselogId(GetExerciseLogDetailReq req) {

        return exerciseLogMapper.deleteByExerciselogId(req);
    }

}
