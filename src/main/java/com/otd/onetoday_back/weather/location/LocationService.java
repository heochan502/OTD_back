package com.otd.onetoday_back.weather.location;

import com.otd.onetoday_back.weather.location.model.LocalNameGetReq;
import com.otd.onetoday_back.weather.location.model.LocalNameGetRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService {
    private final LocationMapper locationMapper;

    // 지역 들고오기
    public List<LocalNameGetRes> getLocalNameAll(LocalNameGetReq req){
        return locationMapper.getLocalNameAll(req);
    }
}
