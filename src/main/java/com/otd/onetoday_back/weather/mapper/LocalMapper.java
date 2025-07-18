package com.otd.onetoday_back.weather.mapper;

import com.otd.onetoday_back.weather.model.LocalDto;
import com.otd.onetoday_back.weather.model.LocalNameGetRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LocalMapper {
    LocalDto getLocalId(int localId);
    List<LocalNameGetRes> getLocalNameAll();
}
