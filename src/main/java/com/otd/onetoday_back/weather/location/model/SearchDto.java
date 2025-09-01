package com.otd.onetoday_back.weather.location.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchDto {
    // address
    private String bldnm;

    // place
    private String title;   // 장소명
    private String road;    // 도로명주소
    private String parcel;  // 지번주소
    private double lat;     // 위도
    private double lon;     // 경도
}
