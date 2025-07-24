package com.otd.onetoday_back.weather.location;

import com.otd.onetoday_back.weather.location.model.LocalNameGetReq;
import com.otd.onetoday_back.weather.location.model.LocalNameGetRes;
import com.otd.onetoday_back.weather.location.model.LocationDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LocationMapper {

    int insertMemberLocation(int memberId, int localId);
    List<LocationDto> getLocalList(LocationDto keyword);
}
