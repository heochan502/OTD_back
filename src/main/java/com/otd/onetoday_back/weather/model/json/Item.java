package com.otd.onetoday_back.weather.model.json;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Item {
    private String baseDate;
    private String baseTime;
    private String category;
    private int nx;
    private int ny;
    // 단기예보
    private String fcstDate;
    private String fcstTime;
    private String fcstValue;
    // 초단기실황
    private String obsrValue;
}
