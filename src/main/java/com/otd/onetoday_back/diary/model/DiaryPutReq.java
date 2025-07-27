package com.otd.onetoday_back.diary.model;

import lombok.Data;

@Data
public class DiaryPutReq {
    private int id;
    private String diaryName;
    private String diaryContent;
    private String diaryImage; // 기존 이미지 유지 or 새 이미지로 변경
}