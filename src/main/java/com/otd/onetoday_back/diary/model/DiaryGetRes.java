package com.otd.onetoday_back.diary.model;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
public class DiaryGetRes {
    private int memo;
    private String diaryName;
    private String diaryContent;
    private String diaryImage;
    private String createdAt;

    @ToString.Exclude
    private String mood;
}