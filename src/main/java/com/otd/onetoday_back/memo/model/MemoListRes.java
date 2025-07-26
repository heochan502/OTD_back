package com.otd.onetoday_back.memo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemoListRes {
    private List<MemoGetRes> memoList;
    private int totalCount;
}
