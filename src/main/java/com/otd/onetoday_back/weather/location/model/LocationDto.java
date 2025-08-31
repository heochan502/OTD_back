package com.otd.onetoday_back.weather.location.model;

import lombok.Data;

@Data //getter, setter, ToString, RAC 포함
public class LocationDto {
    private int addressId;
    private int memberId;
    private String title;
    private String roadAddress;
    private String parcelAddress;
    private double lat;
    private double lon;
    private int nx;
    private int ny;
    private boolean isSelected;
}
