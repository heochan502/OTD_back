package com.otd.onetoday_back.diary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryPostAndUploadRes {
    private int id;
    private String diaryImage;
}