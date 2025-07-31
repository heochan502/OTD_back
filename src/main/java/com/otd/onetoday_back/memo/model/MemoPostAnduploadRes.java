package com.otd.onetoday_back.memo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MemoPostAnduploadRes {
    private int memoId;
    private String memoName;
    private String memoContent;
    private String memoImageFileName;
}