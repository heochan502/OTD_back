package com.otd.onetoday_back.weather.mapper;

import com.otd.onetoday_back.weather.model.LocalDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    LocalDto getMemberId(int memberId);
}
