package com.otd.onetoday_back.health;

import com.otd.onetoday_back.health.model.GetHealthLogDetailReq;
import com.otd.onetoday_back.health.model.GetHealthLogDetailRes;
import com.otd.onetoday_back.health.model.GetHealthLogRes;
import com.otd.onetoday_back.health.model.PostHealthLogDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HealthLogMapper {
int saveHealthLog(PostHealthLogDto dto);
GetHealthLogDetailRes findByHealthlogId(GetHealthLogDetailReq req);
List<GetHealthLogRes> findAllByMemberIdOrderByhealthlogDatetimeDesc(int memberNo);
int deleteByHealthlogId(GetHealthLogDetailReq req);
}
