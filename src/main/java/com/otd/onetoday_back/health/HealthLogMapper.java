package com.otd.onetoday_back.health;

import com.otd.onetoday_back.health.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HealthLogMapper {
int saveHealthLog(PostHealthLogDto dto);
GetHealthLogDetailRes findByHealthlogId(GetHealthLogDetailReq req);
List<GetHealthLogRes> findAllByMemberIdOrderByhealthlogDatetimeDesc(GetHealthLogReq req);
int deleteByHealthlogId(GetHealthLogDetailReq req);

// 건강기록 달력용 날짜
    List<HealthLogCalendarGetRes> findAllByHealthLogDatetime(CalendarDto dto);

}
