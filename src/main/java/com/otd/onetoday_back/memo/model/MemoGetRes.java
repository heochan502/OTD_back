package com.otd.onetoday_back.memo.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemoGetRes {
    private int memoId;
    private int memberNoLogin;
    private String memoName;
    private String memoContent;
    private String memoImageFileName;
    private LocalDateTime createdAt;
}