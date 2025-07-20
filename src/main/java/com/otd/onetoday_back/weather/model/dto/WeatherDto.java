package com.otd.onetoday_back.weather.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class WeatherDto {
    private String baseTime; // 기준 시각
    private String temperature; // 기온
    private String condition; // 날씨 상태
    private String localName; // 지역명
//    private String fcstTime; // 예보시간
}
