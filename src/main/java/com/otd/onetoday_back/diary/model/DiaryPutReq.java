package com.otd.onetoday_back.diary.model;

import lombok.Data;

@Data
public class DiaryPutReq {
    private int id;
    private String diaryName;
    private String diaryContent;
    private String diaryImage;
}