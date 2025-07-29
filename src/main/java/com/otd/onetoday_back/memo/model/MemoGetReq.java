package com.otd.onetoday_back.memo.model;

import lombok.Data;

@Data
public class MemoGetReq {
    private int memberNoLogin;
    private int currentPage;
    private int pageSize;
    private int offset;
}