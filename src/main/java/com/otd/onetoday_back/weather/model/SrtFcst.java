package com.otd.onetoday_back.weather.model;

import lombok.Data;

import java.util.List;

@Data
public class SrtFcst {
    // 초단기예보
    private String tem; // 기온
    private String rh1; // 강수량
    private String sky; // 하늘 상태
    private String pty; // 강수 형태
}
