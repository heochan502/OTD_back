package com.otd.onetoday_back.weather.location.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.beans.ConstructorProperties;

@Getter
@Setter
@ToString
public class LocalNameGetReq {
    private String searchText;
    private Integer page;


    // 안바뀌는 변수명도 적어줘야함
    @ConstructorProperties({"search_text", "page"})
    public LocalNameGetReq(String searchText, Integer page) {
        this.searchText = searchText;
        this.page = page;
    }
}
