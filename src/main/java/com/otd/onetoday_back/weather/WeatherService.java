package com.otd.onetoday_back.weather;

import com.otd.onetoday_back.weather.mapper.LocalMapper;
import com.otd.onetoday_back.weather.model.LocalNameGetReq;
import com.otd.onetoday_back.weather.model.LocalNameGetRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    private final LocalMapper localMapper;

    public List<LocalNameGetRes> getLocalNameAll(LocalNameGetReq req){
        return localMapper.getLocalNameAll(req);
    }
}
