package com.otd.onetoday_back.memo.model;

import lombok.*;

import java.beans.ConstructorProperties;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemoGetReq {
    private int memberNoLogin;
    private String searchText;
    private int currentPage;
    private int pageSize;
    private int offset;
}
