package com.otd.onetoday_back.weather.location;

import com.otd.onetoday_back.weather.location.model.LocalNameGetReq;
import com.otd.onetoday_back.weather.location.model.LocalNameGetRes;
import com.otd.onetoday_back.weather.location.model.LocationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService {
    private final LocationMapper locationMapper;

    public int insertLocation(int memberId, int localId) {
        return locationMapper.insertMemberLocation(localId, memberId);
    }

    public List<LocationDto> getLocalList(LocationDto keyword) {
        return locationMapper.getLocalList(keyword);
    }
}
