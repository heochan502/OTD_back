package com.otd.onetoday_back.weather.location;

import com.otd.onetoday_back.weather.location.model.LocationDto;
import com.otd.onetoday_back.weather.location.model.PostAddressReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LocationMapper {
    void insertAddress(PostAddressReq req);
    int existsAddress(PostAddressReq req);
    List<PostAddressReq> addressList(int memberId);
    int deleteAddress(int id, int memberId);
    int updateSelectedAddress(int memberId, int addressId);
    int clearSelectedAddress(int memberId);
    LocationDto findSelectedAddressByMemberId(int memberId);
}
