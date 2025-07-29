package com.otd.onetoday_back.diary.model;

import lombok.Data;

@Data
public class DiaryGetReq {
    private int memberNoLogin;
    private int currentPage;
    private int pageSize;
    private int offset;

    public void setOffsetFromPage() {
        this.offset = (currentPage - 1) * pageSize;
    }
}
