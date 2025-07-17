package com.otd.onetoday_back.weather.model.feignclient.ultraSrtFcst.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FcstItem {
    private String category;
    private String fcstDate;
    private int fcstTime;
}
