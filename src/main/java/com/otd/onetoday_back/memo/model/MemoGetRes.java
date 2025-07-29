package com.otd.onetoday_back.memo.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemoGetRes {
    private int id;
    private int memberNoLogin;
    private String memoName;
    private String memoContent;
    private String memoImageFileName; // ✅ 조회 시 반환할 이미지 파일명
    private LocalDateTime createdAt;
}