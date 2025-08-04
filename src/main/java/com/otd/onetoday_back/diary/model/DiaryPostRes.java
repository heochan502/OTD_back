package com.otd.onetoday_back.diary.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DiaryPostRes {
    private int diaryId;
    private List<String> diaryImage;
}
