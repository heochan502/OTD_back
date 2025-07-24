package com.otd.onetoday_back.weather.location.model;

import lombok.Data;

@Data //getter, setter, ToString, RAC 포함
public class LocationDto {
    private int membersId;
    // 지역정보
    private int localId;
    private String city;
    private String county;
    private String town;
    private int nx;
    private int ny;

    // 검색기능
    private String keyword;
}
