package com.otd.onetoday_back.weather.model.feignclient.ultraSrtFcst;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WeatherUltraSrtFcstReq {
    private String baseDate;
    private String baseTime;
    private int nx;
    private int ny;
}
