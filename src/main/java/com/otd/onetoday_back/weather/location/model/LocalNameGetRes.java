package com.otd.onetoday_back.weather.location.model;

import lombok.Getter;

@Getter
public class LocalNameGetRes {
    private String city;
    private String county;
    private String town;
    private int nx;
    private int ny;
}
