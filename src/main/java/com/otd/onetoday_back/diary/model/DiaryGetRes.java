package com.otd.onetoday_back.diary.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiaryGetRes {
    private int id;
    private int memberNoLogin;
    private String diaryName;
    private String diaryContent;
    private String imageFileName;
    private LocalDateTime createdAt;
}