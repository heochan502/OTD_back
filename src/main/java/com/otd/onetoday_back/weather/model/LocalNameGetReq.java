package com.otd.onetoday_back.weather.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LocalNameGetReq {
    private String baseDate;
    private String baseTime;
    private int nx;
    private int ny;
}
