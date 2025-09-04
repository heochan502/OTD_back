package com.otd.onetoday_back.weather.location.model;

import lombok.Data;

@Data
public class PutTitleReq {
    private int id;
    private int memberId;
    private String keyword;
}
