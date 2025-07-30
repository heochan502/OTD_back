package com.otd.onetoday_back.memo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MemoListRes {
    @JsonProperty("memoList")
    private List<MemoGetRes> memoList;

    @JsonProperty("totalCount")
    private int totalCount;
}