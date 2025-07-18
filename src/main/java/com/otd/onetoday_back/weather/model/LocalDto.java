package com.otd.onetoday_back.weather.model;

import lombok.Data;

@Data //getter, setter, ToString, RAC 포함
public class LocalDto {
    // 지역정보
    private int localId;
    private String city;
    private String county;
    private String town;
    private int nx;
    private int ny;
}
