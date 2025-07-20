package com.otd.onetoday_back.weather.mapper;

import com.otd.onetoday_back.weather.model.LocalNameGetReq;
import com.otd.onetoday_back.weather.model.LocalNameGetRes;
import com.otd.onetoday_back.weather.model.dto.LocationDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LocationMapper {
    List<LocalNameGetRes> getLocalNameAll(LocalNameGetReq req);
}
