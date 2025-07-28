package com.otd.onetoday_back.memo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoGetReq {
    private Integer memberNoLogin; // 세션에서 Controller가 설정

    private int currentPage = 1;   // 기본값 1
    private int pageSize = 10;     // 기본값 10

    public int offset;

    public int getOffset() {
        return (currentPage - 1) * pageSize;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }
}