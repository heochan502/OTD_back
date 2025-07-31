package com.otd.onetoday_back.diary.model;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
public class DiaryGetRes {
    private int diaryId;
    private String diaryName;
    private String diaryContent;
    private String diaryImage;
    private String createdAt;

    private int memberNoLogin;
    public int getMemberNoLogin() {
        return memberNoLogin;
    }

    @ToString.Exclude
    private String mood;
}