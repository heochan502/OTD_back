package com.otd.onetoday_back.memo.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemoGetRes {
    private int id;
    private String memoName;
    private String memoContent;
    private String createdAt;
    private String memoImage;
    private Integer memberNoLogin;
}