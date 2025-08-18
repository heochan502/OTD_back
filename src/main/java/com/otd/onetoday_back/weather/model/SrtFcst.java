package com.otd.onetoday_back.weather.model;

import lombok.Data;

import java.util.List;

@Data
public class SrtFcst {
    // 초단기예보
    private String fcstTem; // 기온
    private String fcstRn1; // 강수량
    private String fcstSky; // 하늘 상태
    private String fcstPty; // 강수 형태
    private String fcstTime;
}
