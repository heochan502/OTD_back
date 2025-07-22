package com.otd.onetoday_back.memo.model;

import lombok.Getter;
import lombok.ToString;

import java.beans.ConstructorProperties;

@Getter
@ToString
public class MemoGetReq {
    private String searchText;
    private Integer page;

    @ConstructorProperties({"searchText", "page"})
    public MemoGetReq(String searchText, Integer page) {
        this.searchText = searchText;
        this.page = page;
    }
}
