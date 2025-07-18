package com.otd.onetoday_back.weather.model;

import lombok.Data;

@Data
public class WeatherDto {
    private String category;
    private String fcstDate;
    private String fcstTime;
    private String fcstValue;
}
