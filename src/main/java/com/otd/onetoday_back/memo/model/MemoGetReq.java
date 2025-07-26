package com.otd.onetoday_back.memo.model;

import lombok.*;

import java.beans.ConstructorProperties;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemoGetReq {
    private Integer memberNoLogin;
    private String searchText;
    private Integer currentPage;
    private Integer pageSize;
    private Integer offset;
}
