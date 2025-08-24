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
    public List<GetHealthLogRes> findAllByMemberIdOrderByhealthlogDatetimeDesc(GetHealthLogReq req) {
        return healthLogMapper.findAllByMemberIdOrderByhealthlogDatetimeDesc(req);

    }
//    건강기록 리스트 페이징
    public List<GetHealthLogRes> getHealthLogList(int memberId, PagingReq req){
        PagingDto dto = PagingDto.builder()
                .memberId(memberId)
                .size(req.getRowPerPage())
                .startIdx((req.getPage()-1) * req.getRowPerPage())
                .build();
        return healthLogMapper.findByLimitTo(dto);
    }

//    건강기록 달력 날짜
    public List<HealthLogCalendarGetRes> getHealthLogDate(int memberId, HealthLogCalendarGetReq req) {
        MonthRangeDto dto = MonthRangeDto.builder()
                .memberId(memberId)
                .start(req.getStart())
                .end(req.getEnd())
                .build();

        return healthLogMapper.findAllByHealthLogDatetime(dto);
    }

//    건강기록 삭제
    public int deleteByHealthlogId(GetHealthLogDetailReq req) {
        return healthLogMapper.deleteByHealthlogId(req);
    }
}
