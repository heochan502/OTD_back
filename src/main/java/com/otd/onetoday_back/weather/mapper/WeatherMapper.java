package com.otd.onetoday_back.weather.mapper;

import com.otd.onetoday_back.weather.model.dto.LocationDto;
import com.otd.onetoday_back.weather.model.dto.WeatherDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WeatherMapper {
    LocationDto findLocalByMemberId(int memberId);
}
