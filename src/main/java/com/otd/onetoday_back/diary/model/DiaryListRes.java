package com.otd.onetoday_back.diary.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DiaryListRes {
    private List<DiaryGetRes> diaryList;
    private int totalCount;
}
