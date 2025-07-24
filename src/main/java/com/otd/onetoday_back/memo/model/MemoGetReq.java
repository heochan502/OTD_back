package com.otd.onetoday_back.memo.model;

import lombok.*;

import java.beans.ConstructorProperties;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemoGetReq {
    private String searchText;
    private Integer currentPage;
    private Integer pageSize;
    private Integer offset;
}
