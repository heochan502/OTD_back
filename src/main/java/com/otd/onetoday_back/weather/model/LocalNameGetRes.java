package com.otd.onetoday_back.weather.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocalNameGetRes {
    private int localId;
    private String city;
    private String county;
    private String town;
}
