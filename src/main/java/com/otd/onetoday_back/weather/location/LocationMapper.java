package com.otd.onetoday_back.weather.location;

import com.otd.onetoday_back.weather.location.model.LocalNameGetReq;
import com.otd.onetoday_back.weather.location.model.LocalNameGetRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LocationMapper {

    List<LocalNameGetRes> getLocalNameAll(LocalNameGetReq req);
}
