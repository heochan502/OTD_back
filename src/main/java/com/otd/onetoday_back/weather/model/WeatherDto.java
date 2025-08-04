package com.otd.onetoday_back.weather.model;

import lombok.Data;

@Data
public class WeatherDto {
    private String baseTime;
    // 초단기실황(실시간)
    private String tem; // 기온
    private String reh; // 습도
    private String rh1; // 강수량
    private String pty; // 강수형태
    private String localName; // 지역명

    // 단기예보
    private String tmx; // 최고 기온
    private String tmn; // 최저 기온
    private String pop; // 강수 확률;
    private String sky; //하늘 상태
}
