package com.otd.onetoday_back.weather.location;

import com.otd.onetoday_back.weather.location.model.PostAddressReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LocationMapper {
    void insertAddress(PostAddressReq req);
    int existsAddress(PostAddressReq req);
    List<PostAddressReq> addressList(int memberId);
    int deleteAddress(@Param("id") int id, @Param("memberId") int memberId);
}
