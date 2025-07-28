package com.otd.onetoday_back.memo.model;

import lombok.Data;

@Data
public class MemoGetReq {
    private int memberNoLogin;
    private int currentPage = 1;  // 기본값 추가
    private int pageSize = 10;
    private int offset;
}