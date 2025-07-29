package com.otd.onetoday_back.health;

import com.otd.onetoday_back.health.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class HealthLogService {
    private final HealthLogMapper healthLogMapper;

//    건강기록 저장
    public int saveHealthLog(PostHealthLogReq req, int logginMemberId) {
        PostHealthLogDto postHealthLogDto = PostHealthLogDto.builder()
                .healthlogId(req.getHealthlogId())
                .memberId(logginMemberId)
                .weight(req.getWeight())
                .height(req.getHeight())
                .systolicBp(req.getSystolicBp())
                .diastolicBp(req.getDiastolicBp())
                .sugarLevel(req.getSugarLevel())
                .moodLevel(req.getMoodLevel())
                .sleepQuality(req.getSleepQuality())
                .healthlogDatetime(req.getHealthlogDatetime())
                .build();

        return healthLogMapper.saveHealthLog(postHealthLogDto);
    }

//    건강기록 상세 조회
    public GetHealthLogDetailRes findByHealthlogId(GetHealthLogDetailReq req) {
        return healthLogMapper.findByHealthlogId(req);
    }
//    건강기록 목록 조회
    public List<GetHealthLogRes> findAllByMemberIdOrderByHealthlogIdDesc(int logginMemberId) {
        return healthLogMapper.findAllByMemberIdOrderByHealthlogIdDesc(logginMemberId);

    }

//    건강기록 삭제
    public int deleteByHealthlogId(GetHealthLogDetailReq req) {
        return healthLogMapper.deleteByHealthlogId(req);
    }
}
