package com.otd.onetoday_back.weather.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class LocalNameGetRes {
    private String city;
    private String county;
    private String town;
    private int nx;
    private int ny;
}
