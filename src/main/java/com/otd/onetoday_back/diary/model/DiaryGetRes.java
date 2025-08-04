package com.otd.onetoday_back.diary.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiaryGetRes {
    private int diaryId;
    private String diaryName;
    private String diaryContent;
    private String diaryImage;
    private LocalDateTime createdAt;
    private String mood;
    private int memberNoLogin;
}