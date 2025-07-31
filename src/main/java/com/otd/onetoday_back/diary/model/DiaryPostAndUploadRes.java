package com.otd.onetoday_back.diary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
public class DiaryPostAndUploadRes {
    private int diaryId;
    private String diaryImage;
}