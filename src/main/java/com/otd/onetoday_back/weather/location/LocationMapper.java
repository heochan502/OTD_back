package com.otd.onetoday_back.weather.location;

import com.otd.onetoday_back.weather.location.model.LocationDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LocationMapper {

    int insertMemberLocation(LocationDto dto);
    List<LocationDto> getLocalList(LocationDto keyword);
    List<LocationDto> getLocalListByMemberId(int memberId);
//    int unselectAllByMemberId(@Param("memberId") int memberId);
    int updateSelectedLocation(@Param("memberId") int memberId, @Param("localId") int localId);
    int deleteLocation(int memberId, int localId);
}
