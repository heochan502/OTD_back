package com.otd.onetoday_back.diary.model;

import lombok.Data;

@Data
public class DiaryGetOneRes {
    private int diaryId;
    private int memberNoLogin;
    private String diaryName;
    private String diaryContent;
    private String diaryImage;
    private String createdAt;
    private String mood;
}