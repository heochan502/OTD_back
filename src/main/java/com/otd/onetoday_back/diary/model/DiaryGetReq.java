package com.otd.onetoday_back.diary.model;

import lombok.Data;

@Data
public class DiaryGetReq {
    private int memberNoLogin;
    private int currentPage = 1;
    private int pageSize = 10;
    private int offset;
}
