package com.otd.onetoday_back.weather.model;

import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WeatherDto {
    private String baseTime;
    // 초단기실황(실시간)
    private String ncstTem; // 기온
    private String ncstReh; // 습도
    private String ncstRh1; // 강수량
    private String ncstPty; // 강수형태

    // 단기예보
    private String villageTmx; // 최고 기온
    private String villageTmn; // 최저 기온
    private String villagePop; // 강수 확률;
    private String villageSky; // 하늘 상태

    // 초단기예보
    private String fcstTem; // 기온
    private String fcstRh1; // 강수량
    private String fcstSky; // 하늘 상태
    private String fcstPty; // 강수 형태
    private String fcstDate;
    private String fcstTime;

    private String localName; // 지역명
}
