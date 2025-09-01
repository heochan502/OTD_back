package com.otd.onetoday_back.memo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MemoPostRes {
    private int memoId;
    private List<String> memoImage;
}
