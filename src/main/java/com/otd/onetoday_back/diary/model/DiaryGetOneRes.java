package com.otd.onetoday_back.diary.model;

import lombok.Data;

@Data
public class DiaryGetOneRes {
    private int id;
    private int memberNoLogin;
    private String diaryName;
    private String diaryContent;
    private String diaryImage;   // 저장된 이미지 파일명 (단일)
    private String createdAt;    // 예: "2025-07-27 14:12:00"
}