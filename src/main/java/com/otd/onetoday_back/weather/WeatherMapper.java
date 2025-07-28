package com.otd.onetoday_back.weather;

import com.otd.onetoday_back.account.model.memberUpdateDto;
import com.otd.onetoday_back.weather.location.model.LocationDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WeatherMapper {
    LocationDto findLocalByMemberId(int memberId);
    memberUpdateDto getNickName(int memberId);
}
