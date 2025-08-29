package com.otd.onetoday_back.weather.location.model;

import lombok.Data;

@Data
public class PostAddressReq {
    private int id;
    private int memberId;
    private String title;
    private String roadAddress;
    private String parcelAddress;
    private double lat;
    private double lon;
}
